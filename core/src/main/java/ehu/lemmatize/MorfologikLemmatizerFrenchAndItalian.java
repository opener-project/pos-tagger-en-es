package ehu.lemmatize;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import ehu.lemmatize.tagset_mappings.TagsetMappingItalian;
import ehu.lemmatize.tagset_mappings.TagsetMappingsFrench;
import ehu.pos.Resources;
import morfologik.stemming.Dictionary;
import morfologik.stemming.DictionaryLookup;
import morfologik.stemming.IStemmer;
import morfologik.stemming.WordData;

/**
 * Unfortunately French and Italian (or at least French) require a little bit different processing than English or Spanish.
 * This class is to treat them separately for now.
 * @author yo
 *
 */
public class MorfologikLemmatizerFrenchAndItalian implements ehu.lemmatize.Dictionary{

	private static IStemmer frenchDictLookup;
	private static IStemmer italianDictLookup;

	  public MorfologikLemmatizerFrenchAndItalian() throws IllegalArgumentException,
	      IOException {
		  if(frenchDictLookup==null){
			  Resources resourceRetriever = new Resources();
			  URL dictLemmatizer = resourceRetriever.getBinaryDict("fr");
			  frenchDictLookup = new DictionaryLookup(Dictionary.read(dictLemmatizer));
		  }
		  if(italianDictLookup==null){
			  Resources resourceRetriever = new Resources();
			  URL dictLemmatizer = resourceRetriever.getBinaryDict("it");
			  italianDictLookup = new DictionaryLookup(Dictionary.read(dictLemmatizer));
		  }
	  }
	
	@Override
	public String lemmatize(String lang, String word, String postag) {
		if(lang.equalsIgnoreCase("fr")){
			return lemmatizeFrench(word, postag);
		}else if(lang.equalsIgnoreCase("it")){
			return lemmatizeItalian(word, postag);
		}
		throw new RuntimeException("Wrong languge here ("+lang+"). It should be fr or it.");
	}

	
	public String lemmatizeFrench(String word,String postag){
		String lemmaToReturn=word;
		List<WordData> candidates = frenchDictLookup.lookup(word);
		if(candidates==null || candidates.isEmpty()){
			//no candidates, then return the wordform as it is
			//System.err.println("No lemmatization candidates for french ("+word+","+postag+")");
			return lemmaToReturn;
		}
		for(WordData wordData:candidates){
			String lemma=wordData.getStem().toString();
			String tag=wordData.getTag().toString().replace(" ", "");
			//System.err.println("Candidate ("+lemma+","+tag+")");
			String openerCompliantTag=TagsetMappingsFrench.convertFromFtbToKaf(postag);
			String openerCompliantCandidateTag=TagsetMappingsFrench.convertFromLemmatagsToKaf(tag);
			if(openerCompliantCandidateTag.equalsIgnoreCase(openerCompliantTag)){
				lemmaToReturn=lemma;
			}
		}
		//if we arrive here no suitable candidate was found, so return the wordform
		return lemmaToReturn;
	}
	
	public String lemmatizeItalian(String word,String postag){
		List<WordData> candidates = italianDictLookup.lookup(word);
		if(candidates==null || candidates.isEmpty()){
			//no candidates, then return the wordform as it is
			return word;
		}
		//Here I am assuming that the conversion from "lemma-dictionary" tags and "pos-tagger" tags are both done with the same method (i.e. they are equivalent)
		for(WordData wordData:candidates){
			String lemma=wordData.getStem().toString();
			String tag=wordData.getTag().toString();
			String openerCompliantTag=TagsetMappingItalian.convert(postag);
			String openerCompliantCandidateTag=TagsetMappingItalian.convert(tag);
			if(openerCompliantCandidateTag.equalsIgnoreCase(openerCompliantTag)){
				return lemma;
			}
		}
		//if we arrive here no suitable candidate was found, so return the wordform
		return word;
	}
	
}

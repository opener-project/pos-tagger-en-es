/*
 * Copyright 2013 Rodrigo Agerri

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package ehu.pos;


import ixa.kaflib.KAFDocument;
import ixa.kaflib.WF;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ehu.lemmatize.Dictionary;

/**
 * @author ragerri
 * 
 */
public class Annotate {

  private POS posTagger;


  public Annotate(String lang) throws IOException {
    Resources modelRetriever = new Resources();
    InputStream posModel = modelRetriever.getPOSModel(lang);
    posTagger = new POS(posModel);
  }

  
  /**
   * 
   * Mapping between Penn Treebank tagset and KAF tagset
   * 
   * @param penn treebank postag
   * @return kaf POS tag
   */
  private String mapEnglishTagSetToKaf(String postag) {
    if (postag.startsWith("RB")) {
      return "A"; // adverb
    } else if (postag.equalsIgnoreCase("CC")) {
      return "C"; // conjunction
    } else if (postag.startsWith("D") || postag.equalsIgnoreCase("PDT")) {
      return "D"; // determiner and predeterminer
    } else if (postag.startsWith("J")) {
      return "G"; // adjective
    } else if (postag.equalsIgnoreCase("NN") || postag.equalsIgnoreCase("NNS")) {
      return "N"; // common noun
    } else if (postag.startsWith("NNP")) {
      return "R"; // proper noun
    } else if (postag.equalsIgnoreCase("TO") || postag.equalsIgnoreCase("IN")) {
      return "P"; // preposition
    } else if (postag.startsWith("PRP") || postag.startsWith("WP")) {
      return "Q"; // pronoun
    } else if (postag.startsWith("V")) {
      return "V"; // verb
    } else {
      return "O"; // other
    }
  }
  
  private String mapSpanishTagSetToKaf(String postag) { 
    if (postag.equalsIgnoreCase("RB") || postag.equalsIgnoreCase("RN")) {
      return "A"; // adverb
    } else if (postag.equalsIgnoreCase("CC") || postag.equalsIgnoreCase("CS")) {
      return "C"; // conjunction
    } else if (postag.startsWith("D")) {
      return "D"; // determiner and predeterminer
    } else if (postag.startsWith("A")) {
      return "G"; // adjective
    } else if (postag.startsWith("NC")) {
      return "N"; // common noun
    } else if (postag.startsWith("NP")) {
      return "R"; // proper noun
    } else if (postag.startsWith("SP")) {
      return "P"; // preposition
    } else if (postag.startsWith("P")) {
      return "Q"; // pronoun
    } else if (postag.startsWith("V")) {
      return "V"; // verb
    } else {
      return "O"; // other
    }
  }
  
  private String mapDutchTagSetToKaf(String postag) {
    if (postag.startsWith("Adv")) {
      return "A"; // adverb
    } else if (postag.equalsIgnoreCase("Conj")) {
      return "C"; // conjunction
    } else if (postag.startsWith("Art")) {
      return "D"; // determiner 
    } else if (postag.startsWith("Adj")) {
      return "G"; // adjective
    } else if (postag.equalsIgnoreCase("N")) {
      return "N"; // common noun
    } else if (postag.startsWith("N")) {
      return "N"; // proper noun
    } else if (postag.equalsIgnoreCase("Prep")) {
      return "P"; // preposition
    } else if (postag.startsWith("Pron")) {
      return "Q"; // pronoun
    } else if (postag.startsWith("V")) {
      return "V"; // verb
    } else {
      return "O"; // other
    }
  }
  
  /**
   * This enum was used by the French tag mapping, I put it here just to avoid changing the French mapping function
   * @author agarciap
   *
   */
  public static enum KafTag{
    VERB("V"),
    COMMON_NOUN("N"),
    PROPER_NOUN("R"),
    ADJETIVE("G"),
    ADVERB("A"),
    DETERMINER("D"),
    PREPOSITION("P"),
    PRONOUN("Q"),
    CONJUNTION("C"),
    OTHER("O");
    
    private String tagText;
    private KafTag(String tagText){
        this.tagText=tagText;
    }
    
    public String getTagText(){
        return this.tagText;
    }
    
    public String toString(){
        return this.tagText;
    }
}
  
  private String mapFrenchTagSetToKaf(String postag) {
    KafTag kafTag=KafTag.OTHER;
    if(postag.startsWith("V")){
        kafTag=KafTag.VERB;
    }else if(postag.startsWith("NC")){
        kafTag=KafTag.COMMON_NOUN;
    }else if(postag.startsWith("N")){
        kafTag=KafTag.COMMON_NOUN;
    }else if(postag.startsWith("NP")){
        kafTag=KafTag.PROPER_NOUN;
    }else if(postag.startsWith("A")){
        kafTag=KafTag.ADJETIVE;
    }else if(postag.startsWith("Adv")){
        kafTag=KafTag.ADVERB;
    }else if(postag.startsWith("ADV")){
        kafTag=KafTag.ADVERB;
    }else if(postag.startsWith("D")){
        kafTag=KafTag.DETERMINER;
    }else if(postag.startsWith("P")){
        kafTag=KafTag.PREPOSITION;
    }else if(postag.startsWith("PRO")){
        kafTag=KafTag.PRONOUN;
    }else if(postag.startsWith("CL")){ //pronom clitique
        kafTag=KafTag.PRONOUN;
    }else if(postag.startsWith("C")){
        kafTag=KafTag.CONJUNTION;
    }else if(postag.startsWith("CC")){
        kafTag=KafTag.CONJUNTION;
    }else if(postag.startsWith("CS")){
        kafTag=KafTag.CONJUNTION;
    }
    return kafTag.toString();
  }
  
  private String mapItalianTagSetToKaf(String postag) {
    if(postag.startsWith("V"))
      return "V";
  if(postag.startsWith("NOU~C"))
      return "N";
  if(postag.startsWith("NOU~P"))
      return "R";
  if(postag.startsWith("ART"))
      return "D";
  if(postag.startsWith("ADJ"))
      return "G";
  if(postag.startsWith("ADVB"))
      return "A";
  if(postag.startsWith("CONJ"))
      return "Q";
  if(postag.startsWith("PREP"))
      return "P";
  if(postag.startsWith("PRON"))
      return "Q";
  if(postag.startsWith("NUM"))
      return "O.Z";
  return "O";
  }
  
  private String getKafTagSet(String lang, String postag) {
    String tag = null;
    if (lang.equalsIgnoreCase("en")) { 
      tag = this.mapEnglishTagSetToKaf(postag);
    }
    if (lang.equalsIgnoreCase("es")) { 
      tag = this.mapSpanishTagSetToKaf(postag);
    }
    if (lang.equalsIgnoreCase("nl")) { 
      tag = this.mapDutchTagSetToKaf(postag);
    }
    if (lang.equalsIgnoreCase("fr")) { 
      tag = this.mapFrenchTagSetToKaf(postag);
    }
    if (lang.equalsIgnoreCase("it")) { 
      tag = this.mapItalianTagSetToKaf(postag);
    }
    return tag;
  }

  /**
   * Set the term type attribute based on the pos value
   *
   * @param kaf postag
   * @return type
   */
  private String setTermType(String postag) {
    if (postag.startsWith("N") || postag.startsWith("V")
        || postag.startsWith("G") || postag.startsWith("A")) {
      return "open";
    } else {
      return "close";
    }
  }
  
 
  /**
   * This method uses the Apache OpenNLP to perform POS tagging.
   * 
   * It gets a Map<SentenceId, tokens> from the input KAF document and iterates
   * over the tokens of each sentence to annotated POS tags.
   * 
   * It also reads <wf>, elements from the input KAF document and fills the KAF
   * object with those elements plus the annotated POS tags in the <term>
   * elements.
   * 
   * @param LinkedHashMap
   *          <String,List<String>
   * @param List
   *          <Element> termList
   * @param KAF
   *          object. This object is used to take the output data and convert it
   *          to KAF.
   * 
   * @return JDOM KAF document containing <wf>, and <terms> elements.
   */

 
  
  public void annotatePOSToKAF(KAFDocument kaf, Dictionary dictLemmatizer, String lang)
	      throws IOException {

   List<List<WF>> sentences = kaf.getSentences();
   for (List<WF> sentence : sentences) { 
     String[] tokens = new String[sentence.size()];
     for (int i=0; i< sentence.size(); i++) {
       tokens[i] = sentence.get(i).getForm();
     }
	      // POS annotation
	      String[] posTagged = posTagger.posAnnotate(tokens);
	      
	      // KAF building
	      for (int i = 0; i < posTagged.length; i++) {
	        List<WF> wfs = new ArrayList<WF>();
	        wfs.add(sentence.get(i));
	        String posTag = posTagged[i];
	        String posId = this.getKafTagSet(lang, posTag);
	        String type = this.setTermType(posId); // type
	        String lemma = dictLemmatizer.lemmatize(lang, tokens[i], posTag); // lemma
	        kaf.createTermOptions(type, lemma, posId, posTag, wfs);
	      }
	    }
	  }
  
}

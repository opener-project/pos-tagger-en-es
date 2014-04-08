package ehu.lemmatize;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ehu.pos.Resources;

/**
 *
 */
public class SimpleLemmatizer implements Dictionary {
  
	private HashMap<List<String>,String> dictMap;

  public SimpleLemmatizer(InputStream dictionary) {
		dictMap = new HashMap<List<String>,String>();
		BufferedReader breader = new BufferedReader(new InputStreamReader(dictionary));
		String line;
	    try {
			while ((line = breader.readLine()) != null) {
				String[] elems = line.split("\t");
				dictMap.put(Arrays.asList(elems[0],elems[1]),elems[2]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
  }
  
  Resources tagRetriever = new Resources();
  
  private List<String> getDictKeys(String lang, String word, String postag) {
    String constantTag = tagRetriever.setTagConstant(lang, postag);
		List<String> keys = new ArrayList<String>();
		if (postag.startsWith(String.valueOf(constantTag))) { 
			keys.addAll(Arrays.asList(word,postag));
		}
		else {
			keys.addAll(Arrays.asList(word.toLowerCase(),postag));
		}
		return keys;
	}
     
  public String lemmatize(String lang, String word, String postag) {
	String lemma = null;
	String constantTag = tagRetriever.setTagConstant(lang, postag);
	List<String> keys = this.getDictKeys(lang, word, postag);
	//lookup lemma as value of the map
	String keyValue = dictMap.get(keys);
	if (keyValue != null) { 
		lemma = keyValue;
	}
	else if (keyValue == null && postag.startsWith(String.valueOf(constantTag))) { 
		lemma = word;
	}
	else if (keyValue == null && word.toUpperCase() == word) { 
		lemma = word;
	}
	else {
		lemma = word.toLowerCase();
	}
	return lemma;  
  }
}
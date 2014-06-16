package ehu.lemmatize;

import java.io.InputStream;
import java.net.URL;

import ehu.pos.Resources;

/**
 * This class aims to act as a single entry point to get the lemmatizers (Dictionary interface implementing classes). 
 * This is to ease the instantiation from outside employing direct call to Java from JRuby.
 * @author Aitor García Pablos
 *
 */
public class LemmatizerDispatcher {

	/**
	 * Method to get the Morfologik based dictionary
	 * @param lang
	 * @return
	 */
	public static Dictionary obtainMorfologikLemmatizer(String lang) {
		try {
			Resources resourceRetriever = new Resources();
			Dictionary lemmatizer = null;
			//General case for English, Spanish, Dutch (using Rodrigo's classes)
			if (lang.equalsIgnoreCase("en") || lang.equalsIgnoreCase("es")
					|| lang.equalsIgnoreCase("nl")) {
				URL dictLemmatizer = resourceRetriever.getBinaryDict(lang);
				lemmatizer = new MorfologikLemmatizer(dictLemmatizer);
			} 
			//Special case of French and Italian (using Aitor's class)
			else if (lang.equalsIgnoreCase("fr")
					|| lang.equalsIgnoreCase("it")) {
				lemmatizer = new MorfologikLemmatizerFrenchAndItalian();
			} else {
				throw new RuntimeException("Invalid language: " + lang);
			}
			return lemmatizer;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method to get the WordNet based dictionary (only valid for English)
	 * @param lang
	 * @param wnPath
	 * @return
	 */
	public static Dictionary obtainWordNetLemmatizer(String lang, String wnPath) {
		Dictionary lemmatizer;
		try {
			lemmatizer = new JWNLemmatizer(wnPath);
			return lemmatizer;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method to obtain the plain dictionary based lemmatizer
	 * @param lang
	 * @return
	 */
	public static Dictionary obtainSimpleLemmatizer(String lang) {
		Resources resourceRetriever = new Resources();
		InputStream dictLemmatizer = resourceRetriever.getDictionary(lang);
		Dictionary lemmatizer = new SimpleLemmatizer(dictLemmatizer);
		return lemmatizer;
	}
}

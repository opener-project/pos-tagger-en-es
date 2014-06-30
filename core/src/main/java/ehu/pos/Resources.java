package ehu.pos;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

import opennlp.tools.postag.POSModel;

public class Resources {

	private static ConcurrentHashMap<String, POSModel> posModelMap =
        new ConcurrentHashMap<String, POSModel>();

	//private InputStream posModelInputStream;
	//private InputStream dict;
	//private URL dictURL;
	//private String constantTag;

	public InputStream getPOSModelFileInputStream(String langCmdOption) {
		InputStream posModelInputStream=null;
		if (langCmdOption.equals("en")) {
			posModelInputStream = getClass().getResourceAsStream("/en-pos-perceptron-500-dev.bin");
		}

		if (langCmdOption.equals("es")) {
			posModelInputStream = getClass().getResourceAsStream("/es-pos-perceptron-500-0.bin");
		}

		if (langCmdOption.equalsIgnoreCase("nl")) {
			posModelInputStream = getClass().getResourceAsStream("/nl-pos-perceptron.bin");
		}

		if (langCmdOption.equalsIgnoreCase("fr")) {
			posModelInputStream = getClass().getResourceAsStream("/fr-pos-ftb-morpho.bin");
		}

		if (langCmdOption.equalsIgnoreCase("it")) {
			posModelInputStream = getClass().getResourceAsStream("/it-pos-perceptron.bin");
		}

		return posModelInputStream;
	}

	public InputStream getDictionary(String langCmdOption) {
		InputStream dict=null;
		if (langCmdOption.equalsIgnoreCase("en")) {
			dict = getClass().getResourceAsStream("/en-lemmas.dict");
		}

		if (langCmdOption.equalsIgnoreCase("es")) {
			dict = getClass().getResourceAsStream("/es-lemmas.dict");
		}

		if (langCmdOption.equalsIgnoreCase("nl")) {
			dict = getClass().getResourceAsStream("/nl-lemmas.dict");
		}

		return dict;
	}

	public URL getBinaryDict(String langCmdOption) {
		URL dictURL=null;
		if (langCmdOption.equalsIgnoreCase("en")) {
			dictURL = getClass().getResource("/english.dict");
		}

		if (langCmdOption.equalsIgnoreCase("es")) {
			dictURL = getClass().getResource("/spanish.dict");
		}

		if (langCmdOption.equalsIgnoreCase("nl")) {
			dictURL = getClass().getResource("/dutch.dict");
		}

		/*
		 * CHANGE THE PATH TO FRENCH AND ITALIAN DICTS WHEN GENERATED!!!
		 */
		if (langCmdOption.equals("fr")) {
			dictURL = getClass().getResource("/french.dict");
		}
		if (langCmdOption.equals("it")) {
			dictURL = getClass().getResource("/italian.dict");
		}
		// ///////////////////////////////////////////////////////////////

		return dictURL;
	}

	public String setTagConstant(String lang, String postag) {
		String constantTag=null;
		if (lang.equalsIgnoreCase("en")) {
			if (postag.equalsIgnoreCase("NNP")) {
				constantTag = "NNP";
			}
		}
		if (lang.equalsIgnoreCase("es")) {
			if (postag.startsWith("NP")) {
				constantTag = "NP00000";
			}
		}
		if (lang.equalsIgnoreCase("nl")) {
			if (postag.startsWith("N")) {
				constantTag = "N";
			}
		}
		return constantTag;
	}

	public POSModel getPosModel(String lang) {
		POSModel model = posModelMap.get(lang);
		if (model == null) {
			model=loadModel(lang);
			posModelMap.put(lang, model);
		}
		return model;
	}

	private POSModel loadModel(String lang) {
		InputStream modelInputStream = null;
		try {
			modelInputStream = getPOSModelFileInputStream(lang);
			POSModel model = new POSModel(modelInputStream);
			return model;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (modelInputStream != null) {
				try {
					modelInputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}

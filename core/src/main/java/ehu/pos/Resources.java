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

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import opennlp.tools.postag.POSModel;

public class Resources {

	private static Map<String, POSModel> posModelMap = new HashMap<String, POSModel>();

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
		InputStream modelInoutStream = null;
		try {
			modelInoutStream = getPOSModelFileInputStream(lang);
			POSModel model = new POSModel(modelInoutStream);
			return model;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (modelInoutStream != null) {
				try {
					modelInoutStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}

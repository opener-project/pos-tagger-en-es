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

public class Resources {

  private InputStream posModel;
  private InputStream dict;
  private URL dictURL;
  private String constantTag;

  public InputStream getPOSModel(String cmdOption) {

    if (cmdOption.equals("en")) {
      posModel = getClass().getResourceAsStream(
          "/en-pos-perceptron-500-dev.bin");
    }

    if (cmdOption.equals("es")) {
      posModel = getClass().getResourceAsStream("/es-pos-perceptron-500-0.bin");
    }

    if (cmdOption.equalsIgnoreCase("nl")) {
      posModel = getClass().getResourceAsStream("/nl-pos-perceptron.bin");
    }
    
    if (cmdOption.equalsIgnoreCase("fr")) {
      posModel = getClass().getResourceAsStream("/french-pos-treetagger.bin");
    }
    
    if (cmdOption.equalsIgnoreCase("it")) {
      posModel = getClass().getResourceAsStream("/it-pos-perceptron.bin");
    }

    return posModel;
  }

  public InputStream getDictionary(String cmdOption) {
    if (cmdOption.equalsIgnoreCase("en")) {
      dict = getClass().getResourceAsStream("/en-lemmas.dict");
    }

    if (cmdOption.equalsIgnoreCase("es")) {
      dict = getClass().getResourceAsStream("/es-lemmas.dict");
    }

    if (cmdOption.equalsIgnoreCase("nl")) {
      dict = getClass().getResourceAsStream("/nl-lemmas.dict");
    }

    return dict;
  }

  public URL getBinaryDict(String cmdOption) {
    if (cmdOption.equalsIgnoreCase("en")) {
      dictURL = getClass().getResource("/english.dict");
    }

    if (cmdOption.equalsIgnoreCase("es")) {
      dictURL = getClass().getResource("/spanish.dict");
    }

    if (cmdOption.equalsIgnoreCase("nl")) {
      dictURL = getClass().getResource("/dutch.dict");
    }

    /*
     * CHANGE THE PATH TO FRENCH AND ITALIAN DICTS WHEN GENERATED!!!
     */
    if (cmdOption.equals("fr")) {
      dictURL = getClass().getResource("/english.dict");
    }
    if (cmdOption.equals("it")) {
      dictURL = getClass().getResource("/english.dict");
    }
    /////////////////////////////////////////////////////////////////

    return dictURL;
  }

  public String setTagConstant(String lang, String postag) {
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
}

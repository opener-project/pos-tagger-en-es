/*Copyright 2013 Rodrigo Agerri

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

import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

/**
 * Simple POS tagging module based on Apache OpenNLP.
 * 
 * Models trained by IXA NLP Group.
 * 
 * @author ragerri 2012/11/30
 * 
 * 
 * CHANGES: Added a constructor to leverage the static model loading
 * @author agarciap 2014/04/03
 */

public class POS {

 // private POSModel posModel;
  private POSTaggerME posTagger;

  /**
   * It constructs an object POS from the POS class. First it loads a model,
   * then it initializes the nercModel and finally it creates a nercDetector
   * using such model.
   * @deprecated This is the old way of loading the model, reading it from disk every time the constructor is invoked
   */
  @Deprecated
  public POS(InputStream trainedModel) {

    // InputStream trainedModel =
    // getClass().getResourceAsStream("/en-pos-perceptron-1000-dev.bin");

    try {
     POSModel posModel = new POSModel(trainedModel);
     posTagger = new POSTaggerME(posModel);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (trainedModel != null) {
        try {
          trainedModel.close();
        } catch (IOException e) {
        }
      }
    }
    
  }
  
  /**
   * Creates an instance loading the model regarding the language parameter. 
   * The model loading is delegated to the "Resources" class, the models are lazy initialized, and they are loaded only once (a model per language)
   * @param lang
   */
  public POS(String lang) {
	  Resources resources=new Resources();
	  POSModel posModel=resources.getPosModel(lang);
	  posTagger = new POSTaggerME(posModel);
  }

  /**
   * This method receives as an input an array of Apache OpenNLP tokenized text
   * and calls the NameFinderME.find(tokens) to recognize and classify Named
   * Entities.
   * 
   * From Apache OpenNLP documentation: "After every document clearAdaptiveData
   * must be called to clear the adaptive data in the feature generators. Not
   * calling clearAdaptiveData can lead to a sharp drop in the detection rate
   * after a few documents."
   * 
   * @param tokens
   *          an array of tokenized text
   * @return an array of OpenNLP Spans of annotated text
   */
  public String[] posAnnotate(String[] tokens) {
    String[] posTags = posTagger.tag(tokens);
    return posTags;
  }

}
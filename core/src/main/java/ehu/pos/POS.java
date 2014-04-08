package ehu.pos;

import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

/**
 * Simple POS tagging module based on Apache OpenNLP.
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
/**
 * 
 */
package ehu.opennlp.pos.en;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Element;

/**
 * @author ragerri
 * 
 */
public class Annotate {

  private POS posTagger;
  

  public Annotate() {
    posTagger = new POS();
  }

  
  
  /**
   * It reads the linguisticProcessor elements and adds them to
   * the KAF document.
   * 
   * @param lingProc
   * @param kaf
   */
  public void addKafHeader(List<Element> lingProc, KAF kaf) {
    String layer = null;
    for (int i = 0; i < lingProc.size(); i++) {
      layer = lingProc.get(i).getAttributeValue("layer");
      List<Element> lps = lingProc.get(i).getChildren("lp");
      for (Element lp : lps) {
        kaf.addlps(layer, lp.getAttributeValue("name"),
            lp.getAttributeValue("timestamp"), lp.getAttributeValue("version"));
      }

    }

  }

  /**
   * This method uses the Apache OpenNLP to perform POS tagging.
   * 
   * It gets a Map<SentenceId, tokens> from the input KAF document and iterates
   * over the tokens of each sentence to annotated POS tags.
   * 
   * It also reads <wf>, elements from the input KAF document and fills
   * the KAF object with those elements plus the annotated POS tags in the <term>
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

  public void annotatePOSToKAF(
      LinkedHashMap<String, List<String>> sentTokensMap,
      KAF kaf) throws IOException {

    for (Map.Entry<String, List<String>> sentence : sentTokensMap.entrySet()) {
      String sid = sentence.getKey();
      String[] tokens = sentence.getValue().toArray(
          new String[sentence.getValue().size()]);
      
      // POS annotation
      String[] posTagged = posTagger.posAnnotate(tokens);
      
      // Add tokens in the sentence to kaf object
      int numTokensInKaf = kaf.getNumWfs();
      int nextTokenInd = numTokensInKaf + 1;
      for (int i = 0; i < tokens.length; i++) {
        String id = "w" + Integer.toString(nextTokenInd++);
        String tokenStr = tokens[i];
        kaf.addWf(id, sid, tokenStr);
      }

   // Add terms to KAF object
      int noTerms = kaf.getNumTerms();
      int realTermCounter = noTerms + 1;
      int noTarget = numTokensInKaf + 1;
      for (int j = 0; j < posTagged.length; j++) {
        String termId = "t" + Integer.toString(realTermCounter++);
        String posId = posTagged[j];
        String type = "";
        String lemma = "";
        String spanString = tokens[j];
        ArrayList<String> tokenIds = new ArrayList<String>();
        tokenIds.add("w" + Integer.toString(noTarget++));
        kaf.addTerm(termId, posId, type, lemma, tokenIds, spanString);
      }

    }
  }
  

}

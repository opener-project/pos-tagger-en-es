package ehu.lemmatize;

/**
 *
 */
/** 
 * Interface to provide dictionary information for lemmatization.
 */
public interface Dictionary {

  /**
   * Returns the lemma of the specified word with the specified part-of-speech.
   * 
   * @param word The word whose lemmas are desired.
   * @param pos The part-of-speech of the specified word.
   * @return The lemma of the specified word given the specified part-of-speech.
   */
  public String lemmatize(String lang, String word, String postag);

}
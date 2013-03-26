package ehu.opennlp.pos.en;

import java.io.InputStream;

public class Models {

  private InputStream posModel;

  public InputStream getPOSModel(String cmdOption) {

    if (cmdOption.equals("en")) {
      posModel = getClass().getResourceAsStream(
          "/en-pos-perceptron-dev.bin");
    }

    if (cmdOption.equals("es")) {
      posModel = getClass().getResourceAsStream(
          "/en-pos-perceptron-dev.bin");
    }
    return posModel;
  }

}

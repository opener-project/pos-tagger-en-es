package ehu.pos;


import ixa.kaflib.KAFDocument;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
//import java.net.URL;


import net.didion.jwnl.JWNLException;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.MutuallyExclusiveGroup;
import net.sourceforge.argparse4j.inf.Namespace;
import ehu.lemmatize.Dictionary;
import ehu.lemmatize.JWNLemmatizer;
import ehu.lemmatize.LemmatizerDispatcher;
//import ehu.lemmatize.MorfologikLemmatizer;
//import ehu.lemmatize.MorfologikLemmatizerFrenchAndItalian;
import ehu.lemmatize.SimpleLemmatizer;

import org.jdom2.JDOMException;

/**
 * POS tagging using Apache OpenNLP. 
 *
 * @version 1.0
 * 
 */

public class CLI {

  /**
   * 
   * 
   * BufferedReader (from standard input) and BufferedWriter are opened. The
   * module takes KAF and reads the header, and the text elements and uses
   * Annotate class to annotate POS tags and obtain lemmas. The terms elements
   * with POS annotation are then added to the KAF received via standard input.
   * Finally, the modified KAF document is passed via standard output.
   * 
   * @param args
   * @throws IOException
   * @throws JDOMException
   * @throws JWNLException
   */

  public static void main(String[] args) throws IOException, JWNLException, JDOMException {
	  //Decoupled from main:
	  //Main works as before (System.in, System.out), but "execute" method can be used for custom input-output streams
	  execute(System.in, System.out, args);
  }
  
  /**
   * This method decouples the calling to the main functionality from the standard input and standard output allowing better integration as a library.
   * The main method invokes it with the console arguments and the required streams (System.in and System.out) so it should work exactly in the same way.
   * @param is
   * @param os
   * @param args
   * @throws JWNLException
   */
  public static void execute(InputStream is,OutputStream os,String[]args) throws JWNLException{
    Namespace parsedArguments = null;

    // create Argument Parser
    ArgumentParser parser = ArgumentParsers
        .newArgumentParser("ixa-pipe-pos-1.0.jar")
        .description(
            "ehu-pos-1.0 is a multilingual POS tagger module developed by IXA NLP Group based on Apache OpenNLP.\n");
    MutuallyExclusiveGroup group = parser.addMutuallyExclusiveGroup("group"); 
    // specify language
    parser
        .addArgument("-l", "--lang")
        .choices("en", "es", "nl", "fr", "it")
        .required(true)
        .help(
            "It is REQUIRED to choose a language to perform annotation with ehu-pos");
    
    parser.addArgument("-t","--timestamp").action(Arguments.storeTrue()).help("flag to make timestamp static for continous " +
        "integration testing");

    // specify lemmatization method
    
    group.addArgument("-lem","--lemmatize").choices("bin","plain").setDefault("bin").help("Lemmatization method." +
    		"Choose 'bin' for fast binary Morfologik dictionary (this is the default) " +
    		"; 'plain' for plain text dictionary. ");
    
    group
        .addArgument("-w", "--wordnet")
        .required(false)
        .help(
            "Provide path to a dictionary to perform lemmatization with WordNet 3.0. Choices are between WordNet 3.0 and " +
            "an in-house created dictionary");

    
    /*
     * Parse the command line arguments
     */

    // catch errors and print help
    try {
      parsedArguments = parser.parseArgs(args);
    } catch (ArgumentParserException e) {
      parser.handleError(e);
      System.out
          .println("Run java -jar target/ehu-pos-1.0.jar -help for details");
      System.exit(1);
    }

    /*
     * Load language and dictionary parameters and construct annotators, read
     * and write kaf
     */

    // String wnDirectory = args[0];
    
    String lang = parsedArguments.getString("lang");
    String lemMethod = parsedArguments.getString("lemmatize");
    String dictionary = parsedArguments.getString("wordnet");
    BufferedReader breader = null;
    BufferedWriter bwriter = null;
    try {
      breader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
      bwriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
      KAFDocument kaf = KAFDocument.createFromStream(breader);

      // choosing Dictionary for lemmatization
      // WordNet lemmatization available for English only
      // Default: MorfologikLemmatizer
      
      Dictionary lemmatizer = null;
      Resources resourceRetriever = new Resources();
	
      /*
       * NOTE:
       * Corrected the misunderstanding (sorry!), the if-else now consists of:
       *    - if "plain" option is chosen, get the language plain dictionary (any language)
       * 	- else if wn dictionary path provided and language==en, then use JWNLemmatizer
       * 	- else use morfologik dictionary (any language)
       * 
       */
      
      if (lemMethod.equalsIgnoreCase("plain")) {
    	  System.err.println("Using plain text dictionary");
	    	InputStream dictLemmatizer = resourceRetriever.getDictionary(lang);
	    	lemmatizer = new SimpleLemmatizer(dictLemmatizer);
	  }
      
      else if (dictionary != null && lang.equalsIgnoreCase("en")) {
    	  lemmatizer = new JWNLemmatizer(dictionary);
      }
      else{
    	  System.err.println("WordNet lemmatization available for English only. Using" +
    	  		" default Morfologik binary dictionary.");
	      lemmatizer=LemmatizerDispatcher.obtainMorfologikLemmatizer(lang);
      }
      
      // add already contained header plus this module linguistic
      // processor
      Annotate annotator = new Annotate(lang);
      
      if (parsedArguments.getBoolean("timestamp") == true) {
        kaf.addLinguisticProcessor("terms","ehu-pos-"+lang,"now", "1.0");
      }
      else {
        kaf.addLinguisticProcessor("terms", "ehu-pos-"+lang, "1.0");
      }
      
      // annotate POS tags to KAF and lemmatize
      annotator.annotatePOSToKAF(kaf, lemmatizer, lang);
      bwriter.write(kaf.toString());
      bwriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}

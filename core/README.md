
pos-tagger-en-es
================

This module provides a Part of Speech tagger for English, Spanish, Dutch,
French and Italian. 

For installing and using the core of this repository please scroll down to the end of the document for
the [installation instructions](#installation).

## OVERVIEW

This module provides POS tagging and lemmatization for 5 languages. We
provide 5 fast POS tagging models with features based on Collins (2002) paper
on the Perceptron.  

+ **Perceptron model for English** trained and evaluated using the WSJ treebank as explained 
  in K. Toutanova, D. Klein, and C. D. Manning. Feature-rich part-of-speech tagging with a cyclic 
  dependency network. In Proceedings of HLT-NAACL’03, 2003. 
+ **Maximum Entropy model for Spanish** trained and evaluated using the Ancora corpus; it was randomly
  divided in 90% for training (450K words) and 10% testing (50K words). 
+ **POS tagging model for Dutch** publicly available from the Apache OpenNLP
  website: http://opennlp.sourceforge.net/models-1.5/
+ **Dictionary-based lemmatization** for all 5 languages. 

To avoid duplication of efforts, we use the machine learning API 
provided by the [Apache OpenNLP project](http://opennlp.apache.org). 
Additionally, we have added dictionary-based lemmatization for each language. 

Therefore, the following resources are provided within the module: 

+ **English Perceptron POS Model**:
  + Penn Treebank 96.66 word accuracy.

+ **Spanish POS Models**: we obtained better results overall with Maximum Entropy
  models (Ratnapharki 1999). The best results are obtained when a c0 (cutoff 0)
  is used, but those models are slower for production than the Perceptron
  models. Therefore, we provide both types, based on maxent and perceptron.
  + **Ancora Maxent**: 98.88 Word accuracy.
  + **Ancora Perceptron**: 98.24 Word accuracy . 

+ **Dutch POS Perceptron**: downloaded from Apache OpenNLP website: http://opennlp.sourceforge.net/models-1.5/

+ **French POS Maxent**: ESTER model, 

+ **Italian Perceptron model**: 

+ **Lemmatizer Dictionaries for all 5 languages**:
    + **Plain text dictionary**: "Word POStag lemma" dictionary in plain text to perform lemmatization.
    + **Morfologik-stemming**: Binarized palin text dictionaries as a finite state automata 
      using the morfologik-stemming project (see NOTICE file for details). This method uses much less RAM with respect 
      to the plain text dictionary (**this is the default**).

## USAGE

If you are in hurry, just execute: 

````shell
cat file.txt | tokenizer | java -jar $PATH/target/ehu-pos-$version.jar -l $lang
````

If you want to know more, please follow reading.

This pos tagger reads KAF documents (with *wf* and *term* elements) via standard input and outputs KAF
through standard output. 

You can get the necessary input for ixa-pipe-pos by piping it with 
the [OpeNER tokenizer](https://github.com/opener-project/tokenizer). 

There are several options to tag with ixa-pipe-pos: 

+ **lang**: choose between en, es, fr, it and nl.
+ **lemmatize**: choose dictionary method to perform lemmatization:
  + **bin**: Morfologik binary dictionary (**default**).
  + **plain**: plain text dictionary.
  + **wn**: WordNet 3.0-based lemmatization, **only for English**.

To get WordNet go to:

````shell
wget http://wordnetcode.princeton.edu/3.0/WordNet-3.0.tar.gz
````

**Tagging Example**: 

````shell
cat file.txt | tokenizer | java -jar $PATH/target/ehu-pos-$version.jar -l $lang
````

## JAVADOC

It is possible to generate the javadoc of the module by executing:

````shell
cd $repo/core/
mvn javadoc:jar
````

Which will create a jar file core/target/ehu-pos-$version-javadoc.jar

## Module contents

The contents of the core are the following:

    + formatter.xml           Apache OpenNLP code formatter for Eclipse SDK
    + pom.xml                 maven pom file which deals with everything related to compilation and execution of the module
    + src/                    java source code of the module and required resources
    + Furthermore, the installation process, as described in the README.md, will generate another directory:
    target/                 it contains binary executable and other directories


## INSTALLATION

Installing the ehu-pos requires the following steps:

If you already have installed in your machine the Java 1.7+ and MAVEN 3, please go to step 3
directly. Otherwise, follow these steps:

### 1. Install JDK 1.7

If you do not install JDK 1.7 in a default location, you will probably need to configure the PATH in .bashrc or .bash_profile:

````shell
export JAVA_HOME=/yourpath/local/java7
export PATH=${JAVA_HOME}/bin:${PATH}
````

If you use tcsh you will need to specify it in your .login as follows:

````shell
setenv JAVA_HOME /usr/java/java17
setenv PATH ${JAVA_HOME}/bin:${PATH}
````

If you re-login into your shell and run the command

````shell
java -version
````

You should now see that your JDK is 1.7

### 2. Install MAVEN 3

Download MAVEN 3 from

````shell
wget http://apache.rediris.es/maven/maven-3/3.0.5/binaries/apache-maven-3.0.5-bin.tar.gz
````

Now you need to configure the PATH. For Bash Shell:

````shell
export MAVEN_HOME=/home/ragerri/local/apache-maven-3.0.5
export PATH=${MAVEN_HOME}/bin:${PATH}
````

For tcsh shell:

````shell
setenv MAVEN3_HOME ~/local/apache-maven-3.0.5
setenv PATH ${MAVEN3}/bin:{PATH}
````

If you re-login into your shell and run the command

````shell
mvn -version
````

You should see reference to the MAVEN version you have just installed plus the JDK 7 that is using.

### 3. Get module source code

If you must get the module source code from here do this:

````shell
git clone https://github.com/opener-project/pos-tagger-en-es
````

### 4. Compile

````shell
cd $repo/core
mvn clean package
````

This step will create a directory called target/ which contains various directories and files.
Most importantly, there you will find the module executable:

ehu-pos-$version.jar

This executable contains every dependency the module needs, so it is completely portable as long
as you have a JVM 1.7 installed.

To install the module in the local maven repository, usually located in ~/.m2/, execute:

````shell
mvn clean install
````

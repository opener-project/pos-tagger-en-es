# Opener::Kernel::EHU::POSTagger::EN

This module uses Apache OpenNLP programatically to perform POS tagging.
It has been developed by the IXA NLP Group (ixa.si.ehu.es).

+ English perceptron models have been trained and evaluated using the WSJ treebank as explained in
  K. Toutanova, D. Klein, and C. D. Manning. Feature-rich part-of-speech tagging with a cyclic dependency network.
  In Proceedings of HLT-NAACL’03, 2003. Currently we obtain a performance of 96.48% vs 97.24% obtained by Toutanova et al. (2003).

+ Spanish Maximum Entropy models have been trained and evaluated using the Ancora corpus; it was randomly
  divided in 90% for training (440K words) and 10% testing (70K words), obtaining a performance of 98.88%.

## Installation

Add this line to your application's Gemfile:

    gem 'EHU-pos-tagger_EN_kernel', :git=>"git@github.com/opener-project/EHU-pos-tagger_EN_kernel.git"

And then execute:

    $ bundle install

Or install it yourself as:

    $ gem specific_install EHU-pos-tagger_EN_kernel -l https://github.com/opener-project/EHU-pos-tagger_EN_kernel.git


If you dont have specific_install already:

    $ gem install specific_install

## Usage

Once installed as a gem you can access the gem from anywhere:


TODO: Change output below as needed
````shell
echo "foo" | EHU-pos-tagger_EN_kernel
````

Will output

````
oof
````

## Contributing

1. Pull it
2. Create your feature branch (`git checkout -b features/my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin features/my-new-feature`)
5. If you're confident, merge your changes into master.



Contents
========

The contents of the module are the following:

    + formatter.xml           Apache OpenNLP code formatter for Eclipse SDK
    + pom.xml                 maven pom file which deals with everything related to compilation and execution of the module
    + src/                    java source code of the module
    + Furthermore, the installation process, as described in the README.md, will generate another directory:
    target/                 it contains binary executable and other directories


INSTALLATION
============

Installing the ehu-pos requires the following steps:

If you already have installed in your machine JDK6 and MAVEN 3, please go to step 3
directly. Otherwise, follow these steps:

1. Install JDK 1.6
-------------------

If you do not install JDK 1.6 in a default location, you will probably need to configure the PATH in .bashrc or .bash_profile:

````shell
export JAVA_HOME=/yourpath/local/java6
export PATH=${JAVA_HOME}/bin:${PATH}
````

If you use tcsh you will need to specify it in your .login as follows:

````shell
setenv JAVA_HOME /usr/java/java16
setenv PATH ${JAVA_HOME}/bin:${PATH}
````

If you re-login into your shell and run the command

````shell
java -version
````

You should now see that your jdk is 1.6

2. Install MAVEN 3
------------------

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

You should see reference to the MAVEN version you have just installed plus the JDK 6 that is using.

3. Get module source code
--------------------------

````shell
git clone git@github.com:opener-project/EHU-pos-tagger_EN_kernel.git
````

4. Download models and other resources
--------------------------------------

To perform English lemmatization the module uses three different methods for English and two for Spanish:

+ English:
    + WordNet-3.0. You will to give $repo/core/wn30/dict as a value of the -w option when running ehu-pos (see point 7. below).
    + Plain text dictionary: en-lemmas.dict is a "Word POStag lemma" dictionary in plain text to perform lemmatization.
    + Morfologik-stemming: english.dict is the same as en-lemmas.dict but binarized as a finite state automata using the
      morfologik-stemming project (see NOTICE file for details) This method uses 10% of RAM with respect to the plain text
     dictionary and works 2x faster.

+ Spanish:
    + Plain text dictionary: es-lemmas.dict.
    + Morfologik stemming: spanish.dict.

To get WordNet go to:

````shell
wget http://wordnetcode.princeton.edu/3.0/WordNet-3.0.tar.gz
````

5. Move into main directory
---------------------------

````shell
cd $REPO/core
````

6. Install module using maven
-----------------------------

````shell
mvn clean package
````

This step will create a directory called target/ which contains various directories and files.
Most importantly, there you will find the module executable:

ehu-pos-1.0.jar

This executable contains every dependency the module needs, so it is completely portable as long
as you have a JVM 1.6 installed.

To install the module in the local maven repository, usually located at ~/.m2/, execute:

````shell
mvn clean install
````

7. USING ehu-pos
=====================

The program accepts tokenized text in KAF format as standard input and outputs KAF.

You can get the tokenized input for this module from Vicom-tokenizer module. To run the program execute:

````shell
cat wordforms.kaf | java -jar $PATH/target/ehu-pos-1.0.jar -l $lang
````

Current paramaters for specifying the language (to load the relevant models) is mandatory. See

````shell
java -jar $PATH/target/ehu-pos-1.0.jar -help
````

for more options running the module


GENERATING JAVADOC
==================

You can also generate the javadoc of the module by executing:

````shell
mvn javadoc:jar
````

Which will create a jar file target/ehu-pos-1.0-javadoc.jar


Contact information
===================

````shell
Rodrigo Agerri
IXA NLP Group
University of the Basque Country (UPV/EHU)
E-20018 Donostia-San Sebastián
rodrigo.agerri@ehu.es
````

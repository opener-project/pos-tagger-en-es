# Opener::Kernel::EHU::POSTagger::EN

This module provides a simple wrapper that uses Apache OpenNLP
programatically to perform POS tagging in English.
Models have been trained using WSJ treebank (performance 96.48%)
by the IXA NLP Group (ixa.si.ehu.es).

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
    + opener/                 trained models
    + pom.xml                 maven pom file which deals with everything related to compilation and execution of the module
    + src/                    java source code of the module
    + wn30/dict		      WordNet 3.0 dictionary
    + Furthermore, the installation process, as described in the README.md, will generate another directory:
    target/                 it contains binary executable and other directories

- README.md: This README


INSTALLATION
============

Installing the ixa-opennlp-pos-en requires the following steps:

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

3. Get module
-------------

````shell
git clone git@github.com:opener-project/EHU-pos-tagger_EN_kernel.git
````

4. Move into main directory
---------------------------

````shell
cd $repo/core
````

5. Install module using maven
-----------------------------

````shell
mvn clean install
````

This step will create a directory called target/ which contains various directories and files.
Most importantly, there you will find the module executable:

ehu-opennlp-pos-en-1.0.jar

This executable contains every dependency the module needs, so it is completely portable as long
as you have a JVM 1.6 installed.

6. RUNNING EHU-opennlp-pos-en
-----------------------------

The program accepts tokenized text in KAF format as standard input and outputs KAF.

To run the program execute:

````shell
cat wordforms.kaf | java -jar $PATH/target/ehu-opennlp-pos-en-1.0.jar -l $lang -w $wn/dict
````

The paramaters are mandatory. They specify the language to load the models and the path of WordNet
dict directory to obtain the lemmas.


GENERATING JAVADOC
==================

You can also generate the javadoc of the module by executing:

````shell
mvn javadoc:jar
````

Which will create a jar file core/target/ehu-opennlp-pos-en-1.0-javadoc.jar


Contact information
===================

````shell
Rodrigo Agerri
IXA NLP Group
University of the Basque Country (UPV/EHU)
E-20018 Donostia-San Sebastián
rodrigo.agerri@ehu.es
````

[![Build Status](https://drone.io/github.com/opener-project/pos-tagger-en-es/status.png)](https://drone.io/github.com/opener-project/pos-tagger-en-es/latest)

# English, Spanish, Italian, French POS Tagger

**Modified to also do Dutch**

This repository contains the source code for the English & Spanish POS tagger of the
OpeNER project.

English perceptron models have been trained and evaluated using the WSJ
treebank as explained in K. Toutanova, D. Klein, and C. D. Manning.
Feature-rich part-of-speech tagging with a cyclic dependency network.  In
Proceedings of HLT-NAACL’03, 2003. Currently we obtain a performance of 96.48%
vs 97.24% obtained by Toutanova et al. (2003).

Spanish Maximum Entropy models have been trained and evaluated using the Ancora 
corpus; it was randomly divided in 90% for training (440K words) and 10% testing
(70K words), obtaining a performance of 98.88%.

## Requirements

* Java 1.7 or newer
* Ruby 1.9.2 or newer
* Maven
* Bundler

## Installation

Using RubyGems:

    gem install opener-pos-tagger-en-es

Using Bundler:

    gem 'opener-pos-tagger-en-es',
      :git    => 'git@github.com/opener-project/pos-tagger-en-es.git',
      :branch => 'master'

Using specific install:

    gem install specific_install
    gem specific_install opener-pos-tagger-en-es \
        -l https://github.com/opener-project/pos-tagger-en-es.git

## Usage

    cat some_input_file.kaf | pos-tagger-en-es

## Contributing

First make sure all the required dependencies are installed:

    bundle install

Then compile the required Java code:

    bundle exec rake java:compile

For this you'll need to have Java 1.7 and Maven installed. These requirements
are verified for you before the Rake task calls Maven.

## Testing

To run the tests (which are powered by Cucumber), simply run the following:

    bundle exec rake

This will take care of verifying the requirements, installing the required Java
packages and running the tests.

For more information on the available Rake tasks run the following:

    bundle exec rake -T

## Structure

This repository comes in two parts: a collection of Java source files and Ruby
source files. The Java code can be found in the `core/` directory, everything
else will be Ruby source code.

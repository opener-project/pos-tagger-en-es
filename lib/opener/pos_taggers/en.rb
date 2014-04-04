require 'open3'
require 'stringio'

require 'java'

require File.expand_path('../../../../core/target/ehu-pos-1.0.jar', __FILE__)

import 'java.io.InputStreamReader'
import 'ixa.kaflib.KAFDocument'
import 'ehu.pos.Annotate'
import 'ehu.pos.Resources'
import 'ehu.lemmatize.MorfologikLemmatizer'
import 'ehu.lemmatize.Dictionary'

require_relative 'en/version'

module Opener
  module POSTaggers
    ##
    # The POS tagger that supports English and Spanish.
    #
    # @!attribute [r] args
    #  @return [Array]
    # @!attribute [r] options
    #  @return [Hash]
    #
    class EN
      attr_reader :args, :options

      ##
      # The default language to use.
      #
      # @return [String]
      #
      DEFAULT_LANGUAGE = 'en'.freeze

      ##
      # @param [Hash] options
      #
      # @option options [Array] :args The commandline arguments to pass to the
      #  underlying Python script.
      #
      def initialize(options = {})
        @args    = options.delete(:args) || []
        @options = options
      end

      ##
      # Runs the command and returns the output of STDOUT, STDERR and the
      # process information.
      #
      # @param [String] input The input to tag.
      # @return [Array]
      #
      def run(input)
        input     = StringIO.new(input) unless input.kind_of?(IO)
        reader    = InputStreamReader.new(input.to_inputstream)
        kaf       = KAFDocument.create_from_stream(reader)
        annotator = Java::ehu.pos.Annotate.new(language)

        kaf.addLinguisticProcessor("terms","ehu-pos-"+language,"now","1.0");
        annotator.annotatePOSToKAF(kaf, lemmatizer, language)

        return kaf.to_string
      end

      protected

      def dictionary
        Resources.new.getBinaryDict(language)
      end

      def lemmatizer
        MorfologikLemmatizer.new(dictionary)
      end

      ##
      # @return [String]
      #
      def language
        return options[:language] || DEFAULT_LANGUAGE
      end
    end # EN

    class ES < EN
      ##
      # @return [String]
      #
      def language
        return 'es'
      end
    end # ES

    class NL < EN
      def language
        return 'nl'
      end
    end # NL
    
    class IT < EN
      ##
      # @return [String]
      #
      def language
        return 'it'
      end
    end # IT
    
    class FR < EN
      ##
      # @return [String]
      #
      def language
        return 'fr'
      end
    end # FR
  end # POSTaggers
end # Opener

require 'open3'
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
      # Builds the command used to execute the kernel.
      #
      # @return [String]
      #
      def command
        "java -jar #{kernel} -l #{language} #{args.join(' ')}"
      end

      ##
      # Runs the command and returns the output of STDOUT, STDERR and the
      # process information.
      #
      # @param [String] input The input to tag.
      # @return [Array]
      #
      def run(input)
        return Open3.capture3(command, :stdin_data => input)
      end

      protected

      ##
      # @return [String]
      #
      def core_dir
        File.expand_path("../../../core", File.dirname(__FILE__))
      end

      ##
      # @return [String]
      #
      def kernel
        core_dir+'/target/ehu-pos-1.0.jar'
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
  end # POSTaggers
end # Opener

module Opener
  module POSTaggers
    ##
    # Base POS tagger class for the various language specific ones such as
    # {OpeneR::POSTaggers::FR}.
    #
    # @!attribute [r] args
    #  @return [Array]
    #
    # @!attribute [r] options
    #  @return [Hash]
    #
    class EnEs
      attr_reader :args, :options

      ##
      # The default options to use.
      #
      # @return [Hash]
      #
      DEFAULT_OPTIONS = {
        :enable_time => true
      }

      ##
      # @param [Hash] options
      #
      # @option options [Array] :args
      #
      # @option options [TrueClass|FalseClass] :enable_time When set to `true`
      #  (default) dynamic timestamps will be added.
      #
      def initialize(options = {})
        @args    = options.delete(:args) || []
        @options = DEFAULT_OPTIONS.merge(options)
      end

      ##
      # Runs the command and returns the resulting KAF document.
      #
      # @param [String] input The input to tag.
      # @return [Array]
      #
      def run(input)
        language = language_from_kaf(input)
        input    = StringIO.new(input)

        reader    = Java::java.io.InputStreamReader.new(input.to_inputstream)
        kaf       = Java::ixa.kaflib.KAFDocument.create_from_stream(reader)
        annotator = new_annotator(language)

        annotator.annotatePOSToKAF(kaf, lemmatizer(language), language)

        return kaf.to_string
      end

      protected

      ##
      # Creates and configures a new annotator instance.
      #
      # @param [String] language
      # @return [Java::ehy.pos.Annotate]
      #
      def new_annotator(language)
        annotator = Java::ehu.pos.Annotate.new(language)

        annotator.disableTimestamp unless options[:enable_time]

        return annotator
      end

      ##
      # Returns the lemmatizer to use.
      #
      # @param [String] language
      #
      def lemmatizer(language)
        return Java::ehu.lemmatize.LemmatizerDispatcher.obtainMorfologikLemmatizer(language)
      end

      ##
      # Returns the language for the given KAF document.
      #
      # @param [String] input
      # @return [String]
      #
      def language_from_kaf(input)
        document = Nokogiri::XML(input)

        return document.at('KAF').attr('xml:lang')
      end
    end # Base
  end # POSTaggers
end # Opener

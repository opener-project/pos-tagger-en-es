module Opener
  module POSTaggers
    ##
    # Dutch POS tagger class. This class forces the language to Dutch
    # regardless of what the KAF document claims the language to be.
    #
    class NL < EnEs
      ##
      # @see [Opener::POSTaggers::Base#language_from_kaf]
      #
      def language_from_kaf(input)
        return 'nl'
      end
    end # NL
  end # POSTaggers
end # Opener

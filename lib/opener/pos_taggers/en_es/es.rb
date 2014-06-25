module Opener
  module POSTaggers
    ##
    # Spanish POS tagger class. This class forces the language to Spanish
    # regardless of what the KAF document claims the language to be.
    #
    class ES < EnEs
      ##
      # @see [Opener::POSTaggers::Base#language_from_kaf]
      #
      def language_from_kaf(input)
        return 'es'
      end
    end # ES
  end # POSTaggers
end # Opener

module Opener
  module POSTaggers
    ##
    # English POS tagger class. This class forces the language to English
    # regardless of what the KAF document claims the language to be.
    #
    class EN < EnEs
      ##
      # @see [Opener::POSTaggers::Base#language_from_kaf]
      #
      def language_from_kaf(input)
        return 'en'
      end
    end # EN
  end # POSTaggers
end # Opener

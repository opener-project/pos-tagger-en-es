module Opener
  module POSTaggers
    ##
    # Italian POS tagger class. This class forces the language to Italian
    # regardless of what the KAF document claims the language to be.
    #
    class IT < EnEs
      ##
      # @see [Opener::POSTaggers::Base#language_from_kaf]
      #
      def language_from_kaf(input)
        return 'it'
      end
    end # IT
  end # POSTaggers
end # Opener

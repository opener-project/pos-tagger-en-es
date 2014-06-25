module Opener
  module POSTaggers
    ##
    # French POS tagger class. This class forces the language to French
    # regardless of what the KAF document claims the language to be.
    #
    class FR < EnEs
      ##
      # @see [Opener::POSTaggers::Base#language_from_kaf]
      #
      def language_from_kaf(input)
        return 'fr'
      end
    end # FR
  end # POSTaggers
end # Opener

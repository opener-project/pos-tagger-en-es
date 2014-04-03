package ehu.lemmatize.tagset_mappings;

public class TagsetMappingsFrench {

	public static enum KafTag {
		VERB("V"), COMMON_NOUN("N"), PROPER_NOUN("R"), ADJETIVE("G"), ADVERB("A"), DETERMINER("D"), PREPOSITION("P"), PRONOUN(
				"Q"), CONJUNTION("C"), OTHER("O");

		private String tagText;

		private KafTag(String tagText) {
			this.tagText = tagText;
		}

		public String getTagText() {
			return this.tagText;
		}

		public String toString() {
			return this.tagText;
		}
	}

	public static String convertFromFtbToKaf(String frenchTreebankTag) {
		KafTag kafTag = KafTag.OTHER;
		if (frenchTreebankTag.startsWith("V")) {
			kafTag = KafTag.VERB;
		} else if (frenchTreebankTag.startsWith("NC")) {
			kafTag = KafTag.COMMON_NOUN;
		} else if (frenchTreebankTag.startsWith("N")) {
			kafTag = KafTag.COMMON_NOUN;
		} else if (frenchTreebankTag.startsWith("NP")) {
			kafTag = KafTag.PROPER_NOUN;
		} else if (frenchTreebankTag.startsWith("A")) {
			kafTag = KafTag.ADJETIVE;
		} else if (frenchTreebankTag.startsWith("Adv")) {
			kafTag = KafTag.ADVERB;
		} else if (frenchTreebankTag.startsWith("ADV")) {
			kafTag = KafTag.ADVERB;
		} else if (frenchTreebankTag.startsWith("D")) {
			kafTag = KafTag.DETERMINER;
		} else if (frenchTreebankTag.startsWith("P")) {
			kafTag = KafTag.PREPOSITION;
		} else if (frenchTreebankTag.startsWith("PRO")) {
			kafTag = KafTag.PRONOUN;
		} else if (frenchTreebankTag.startsWith("CL")) { // pronom clitique
			kafTag = KafTag.PRONOUN;
		} else if (frenchTreebankTag.startsWith("C")) {
			kafTag = KafTag.CONJUNTION;
		} else if (frenchTreebankTag.startsWith("CC")) {
			kafTag = KafTag.CONJUNTION;
		} else if (frenchTreebankTag.startsWith("CS")) {
			kafTag = KafTag.CONJUNTION;
		}
		// System.out.println("(ftb-to-kaf) Entering "+frenchTreebankTag+", output "+kafTag.toString());
		return kafTag.toString();
	}

	public static String convertFromLemmatagsToKaf(String lemmaTag) {
		KafTag kafTag = KafTag.OTHER;
		if (lemmaTag.equalsIgnoreCase("V")) {
			kafTag = KafTag.VERB;
		} else if (lemmaTag.equalsIgnoreCase("NC")) {
			kafTag = KafTag.COMMON_NOUN;
		} else if (lemmaTag.equalsIgnoreCase("NP")) {
			kafTag = KafTag.PROPER_NOUN;
		} else if (lemmaTag.equalsIgnoreCase("adj")) {
			kafTag = KafTag.ADJETIVE;
		} else if (lemmaTag.equalsIgnoreCase("adv")) {
			kafTag = KafTag.ADVERB;
		} else if (lemmaTag.equalsIgnoreCase("det")) {
			kafTag = KafTag.DETERMINER;
		} else if (lemmaTag.equalsIgnoreCase("prep")) {
			kafTag = KafTag.PREPOSITION;
		} else if (lemmaTag.equalsIgnoreCase("PRO")) {
			kafTag = KafTag.PRONOUN;
		} else if (lemmaTag.equalsIgnoreCase("ce")) {
			kafTag = KafTag.CONJUNTION;
		} else if (lemmaTag.equalsIgnoreCase("csu")) {
			kafTag = KafTag.CONJUNTION;
		} else if (lemmaTag.equalsIgnoreCase("auxAvoir")) {
			kafTag = KafTag.VERB;
		} else if (lemmaTag.equalsIgnoreCase("auxEtre")) {
			kafTag = KafTag.VERB;
		}
		// System.err.println("(lemma-to-kaf) Entering "+lemmaTag+", output "+kafTag.toString());
		return kafTag.toString();
	}

	/*
	 * Lemmatization tagset det np auxAvoir adj caimp suffAdj cld cla cldr que
	 * 100 adv clr ilimp cln clneg prep v pri prel nc auxEtre pro clar csu ce
	 */

	/*
	 * French TreeBank tagset - A (adjective) - Adv (adverb) - CC (coordinating
	 * conjunction) - Cl (weak clitic pronoun) - CS (subordinating conjunction)
	 * - D (determiner) - ET (foreign word) - I (interjection) - NC (common
	 * noun) - NP (proper noun) - P (preposition) - PREF (prefix) - PRO (strong
	 * pronoun) - V (verb) - PONCT (punctuation mark)
	 */

	/*
	 * KAF POSTAGS N common noun V verb R proper noun P preposition Q Pronoun A
	 * adverb D Determiner C conjunction G Adjective O other
	 */

}

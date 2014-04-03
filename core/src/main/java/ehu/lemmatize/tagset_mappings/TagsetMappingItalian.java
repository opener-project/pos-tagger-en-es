package ehu.lemmatize.tagset_mappings;

public class TagsetMappingItalian {

	public static String convert(String ptb) {

		if (ptb.startsWith("V"))
			return "V";
		if (ptb.startsWith("NOU~C"))
			return "N";
		if (ptb.startsWith("NOU~P"))
			return "R";
		if (ptb.startsWith("ART"))
			return "D";
		if (ptb.startsWith("ADJ"))
			return "G";
		if (ptb.startsWith("ADVB"))
			return "A";
		if (ptb.startsWith("CONJ"))
			return "Q";
		if (ptb.startsWith("PREP"))
			return "P";
		if (ptb.startsWith("PRON"))
			return "Q";
		if (ptb.startsWith("NUM"))
			return "O.Z";
		return "O";
	}

}

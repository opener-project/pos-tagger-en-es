package ehu.pos;

import static org.junit.Assert.*;
import ixa.kaflib.KAFDocument;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import ehu.lemmatize.Dictionary;
import ehu.lemmatize.MorfologikLemmatizer;
import ehu.lemmatize.MorfologikLemmatizerFrenchAndItalian;

public class AnnotateTest {

	Dictionary lemmatizer = null;
    Resources resourceRetriever = new Resources();
	
	private static Map<String,String>fixturesMap;
	private String[]fixtureFilePaths={
			"test_kafs/en-kaf-with-tokens.kaf",
			"test_kafs/es-kaf-with-tokens.kaf",
			"test_kafs/fr-kaf-with-tokens.kaf",
			"test_kafs/it-kaf-with-tokens.kaf",			
	};
	
	//private static Map<String,String>expectResults;
	
	@Before
	public void setUp() throws Exception {
		fixturesMap=new HashMap<String,String>();
		for(String filePath:fixtureFilePaths){
			InputStream is=AnnotateTest.class.getClassLoader().getResourceAsStream(filePath);
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			IOUtils.copy(is, bos);
			String lang=getFixtureFileLanguage(filePath);
			fixturesMap.put(lang, bos.toString());
		}
		
		
	}

	@Test
	public void testAnnotatePOSToKAF_EN() {
		try {
			String lang="en";
			Annotate annotate=new Annotate(lang);
			String kafWithTokenString=fixturesMap.get(lang);
			KAFDocument kaf=KAFDocument.createFromStream(new StringReader(kafWithTokenString));
			URL dictLemmatizer = resourceRetriever.getBinaryDict(lang);
		    lemmatizer = new MorfologikLemmatizer(dictLemmatizer);
			annotate.annotatePOSToKAF(kaf, lemmatizer, lang);
			
			String expectedKaf=FileUtils.readFileToString(new File(AnnotateTest.class.getClassLoader().getResource("test_kafs/"+lang+"-kaf-with-terms.kaf").getPath()));
			assertEquals(expectedKaf.trim(), kaf.toString().trim());
			//System.out.println(kaf.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Test
	public void testAnnotatePOSToKAF_ES() {
		try {
			String lang="es";
			Annotate annotate=new Annotate(lang);
			String kafWithTokenString=fixturesMap.get(lang);
			KAFDocument kaf=KAFDocument.createFromStream(new StringReader(kafWithTokenString));
			URL dictLemmatizer = resourceRetriever.getBinaryDict(lang);
		    lemmatizer = new MorfologikLemmatizer(dictLemmatizer);
			annotate.annotatePOSToKAF(kaf, lemmatizer, lang);
			
			String expectedKaf=FileUtils.readFileToString(new File(AnnotateTest.class.getClassLoader().getResource("test_kafs/"+lang+"-kaf-with-terms.kaf").getPath()));
			assertEquals(expectedKaf.trim(), kaf.toString().trim());
			
//			System.out.println(kaf.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void testAnnotatePOSToKAF_FR() {
		try {
			String lang="fr";
			Annotate annotate=new Annotate(lang);
			String kafWithTokenString=fixturesMap.get(lang);
			KAFDocument kaf=KAFDocument.createFromStream(new StringReader(kafWithTokenString));
			//URL dictLemmatizer = resourceRetriever.getBinaryDict(lang);
		    lemmatizer = new MorfologikLemmatizerFrenchAndItalian();
			annotate.annotatePOSToKAF(kaf, lemmatizer, lang);
			
			//System.out.println(kaf.toString());
			
			String expectedKaf=FileUtils.readFileToString(new File(AnnotateTest.class.getClassLoader().getResource("test_kafs/"+lang+"-kaf-with-terms.kaf").getPath()));
			assertEquals(expectedKaf.trim(), kaf.toString().trim());
			
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void testAnnotatePOSToKAF_IT() {
		try {
			String lang="it";
			Annotate annotate=new Annotate(lang);
			String kafWithTokenString=fixturesMap.get(lang);
			KAFDocument kaf=KAFDocument.createFromStream(new StringReader(kafWithTokenString));
			//URL dictLemmatizer = resourceRetriever.getBinaryDict(lang);
		    lemmatizer = new MorfologikLemmatizerFrenchAndItalian();
			annotate.annotatePOSToKAF(kaf, lemmatizer, lang);
			
			System.out.println(kaf.toString());
			
			String expectedKaf=FileUtils.readFileToString(new File(AnnotateTest.class.getClassLoader().getResource("test_kafs/"+lang+"-kaf-with-terms.kaf").getPath()));
			assertEquals(expectedKaf.trim(), kaf.toString().trim());
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	
	protected String getFixtureFileLanguage(String filePath){
		File file=new File(filePath);
		return file.getName().substring(0, 2);
	}
}

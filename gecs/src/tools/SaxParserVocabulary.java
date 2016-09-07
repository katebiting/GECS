package tools;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
//classe che permette di accedere al file xml

public class SaxParserVocabulary {
	
	 public  HashMap<String, String> parserVocabulary() {
		 
		SAXParserFactory factory = SAXParserFactory.newInstance();
		 SaxHandler handler = SaxHandler.getVocabulary();
	        try {
	            InputStream xmlInput  =  new FileInputStream("/Users/Caterina/Downloads/enwik9");
	            SAXParser saxParser = factory.newSAXParser();
	            saxParser.parse(xmlInput, handler);
		
	 } catch (Throwable err) {
	            err.printStackTrace ();
	        }
	        return handler.getVocabularyResult();
	    }
	
}

package tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class SaxHandler extends DefaultHandler {

   boolean title = false;
   private static SaxHandler saxVocabulary;
   private static HashMap<String, String> vocabulary;
   
   public static SaxHandler getVocabulary(){
	   saxVocabulary = new SaxHandler();
	   vocabulary = new HashMap<>();
   return saxVocabulary;
   }

   //metodo che cerca all'interno del file xml il tag <title>
   public void startElement(String uri, 
   String localName, String qName, Attributes attributes) throws SAXException {
      if (qName.equalsIgnoreCase("title")) {
    	  title = true;
      }
   }
   
   //metodo che memorizza il contenuto del tag nel vocabolario
   public void characters(char ch[], int start, int length) throws SAXException {
	   if (title) {
		   String word = new String(ch, start, length);  
		   String alphaOnly = word.replaceAll("[^a-zA-Z]+"," ");
		   String m = new String();
		   String b = new String();
		   List<String> tokenize = Twokenize.tokenize(alphaOnly);
		   for(String str : tokenize){
			  /* if(str.startsWith("a") || str.startsWith("b") || str.startsWith("c") ||str.startsWith("d") ||str.startsWith("e") ||str.startsWith("f") 
							||str.startsWith("g") ||str.startsWith("h") ||str.startsWith("i") ||str.startsWith("j") ||str.startsWith("k") ||str.startsWith("l") 
							||str.startsWith("m") || str.startsWith("n") ||str.startsWith("o") ||str.startsWith("p") ||str.startsWith("q") ||str.startsWith("r") 
							||str.startsWith("s") ||str.startsWith("t") ||str.startsWith("u") ||str.startsWith("v") ||str.startsWith("w") ||str.startsWith("x") 
							||str.startsWith("y") ||str.startsWith("z"))*/
			   if(Character.isLowerCase(str.charAt(0)))	
				   vocabulary.put(str, str);
			   else{
					   try{
						   String[] alphaMin = str.split("[A-Z]+");
						   String[] alphaMai = str.split("[a-z]+");
					   
						   for(int i=1; i< alphaMai.length+1; i++){
							   b = alphaMai[i-1];
							   m = alphaMin[i];
							   vocabulary.put(b.concat(m).toLowerCase(), b.concat(m).toLowerCase());
							  
						   }
					   }catch (Exception e) {
						   vocabulary.put(b.toLowerCase(), b.toLowerCase());	
						   
					   }
	
					   vocabulary.remove("");
			   }
		   } 
	title = false;
	   }
   }
   
   public HashMap<String,String> getVocabularyResult() {
		return vocabulary;
	}
}


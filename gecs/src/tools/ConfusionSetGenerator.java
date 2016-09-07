package tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tools.DoubleMetaphone;
import tools.LevenshteinDistance;
import tools.Number2String;
/*generazione del confusion set associato ad ogni parola OOV*/
public class ConfusionSetGenerator {

	private SaxParserVocabulary parser;
	private HashMap<String,String> dictionary;
	//costruttore
	public ConfusionSetGenerator(){
		parser = new SaxParserVocabulary();
		dictionary=parser.parserVocabulary();        //Dizionario Wikipedia, se si usa questo commentare Unix negli altri metodi
		
	}

	//metodo che permette, dato un tweet, l'individuazione delle parole Out Of Vocaboulary
	public List<String> oovDetector(List<String> tweet) throws IOException{

		/*dizionario Unix
		FileInputStream fis = new FileInputStream("web2");
		//Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		HashMap<String,String> map = new HashMap<String,String>();
		String li;
		while ( (li = br.readLine()) != null) {
			map.put(li, li);
		}
		br.close();
		dictionary = map;
		fine lettura dizionario*/



		System.out.println("------Searching for OOV in this tweet...------");
		List<String> oov = new ArrayList<String>();

		for (int i=0; i<tweet.size(); i++){
			if(!tweet.get(i).startsWith("#")&&!tweet.get(i).startsWith("@")){
				if (!dictionary.containsKey(tweet.get(i)))
					oov.add(tweet.get(i));
			}
		}
		if(oov.size()==0)
			System.out.println("no OOV detected");
		else{
			System.out.println("OOV detected: ");
			for(String s : oov)
				System.out.println(s);
		}
		return oov;
	}

	//metodo che riduce l'occorrenza di un carattere in una parola ad un massimo di tre ripetizioni consecutive (oooook -> oook)
	public String deleteRepetition (String oov){
		boolean three = false;
		int i = 0;
		String result = "";
		if(oov.length()>3){
			while(i<oov.length()-2){
				if(three){
					if(oov.charAt(i)!=oov.charAt(i+1)){
						three = false;

					}
				}
				else		
					if(oov.charAt(i)==oov.charAt(i+1) && oov.charAt(i+1)==oov.charAt(i+2)){
						result+= oov.charAt(i);
						result+= oov.charAt(i+1);
						result+= oov.charAt(i+2);
						i++;
						three=true;
					}else
						result+=oov.charAt(i);
				i++;

			}
			if(!three){
				result+=oov.charAt(oov.length()-2);
				if(!(oov.charAt(oov.length()-4)==oov.charAt(oov.length()-3) && oov.charAt(oov.length()-3)==oov.charAt(oov.length()-2)))
					result+=oov.charAt(oov.length()-1);
			}else
				if(oov.charAt(oov.length()-2)!=oov.charAt(oov.length()-1))
					result+=oov.charAt(oov.length()-1);


		}else
			result = oov;
		return result;
	}

	//metodo che restituisce il confusion set relativo 
	public Map<String,List<String>> createConfusionSet(List<String> oov) throws IOException{
		Map<String,List<String>> allConfusionSet = new HashMap<String,List<String>>();
		List<String> confusionSet;

		/*dizionario Unix
		FileInputStream fis = new FileInputStream("web2");
		//Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		HashMap<String,String> map = new HashMap<String,String>();
		String li;
		while ( (li = br.readLine()) != null) {
			map.put(li, li);
		}
		br.close();
		dictionary = map;
		fine lettura dizionario*/



		String oovAdj;
		LevenshteinDistance lev = new LevenshteinDistance();
		int characterEditDistance = 0;
		int phoneticEditDistance = 0;
		DoubleMetaphone d = new DoubleMetaphone();
		Number2String n = new Number2String();

		for (int i=0; i<oov.size(); i++){

			oovAdj = deleteRepetition(oov.get(i));
			confusionSet = new ArrayList<String>();
			Set<String> dict = dictionary.keySet();
			System.out.println("------Confusion set for "+oovAdj+"------");

			for(String s : dict){
				characterEditDistance = lev.levenshteinDistance(n.number2String(oovAdj),s);
				String phonOov = d.doubleMetaphone(n.number2String(oovAdj));
				String phonIv = d.doubleMetaphone(n.number2String(s));
				phoneticEditDistance = lev.levenshteinDistance(phonOov, phonIv);

				if(characterEditDistance <= 2 || phoneticEditDistance <= 1){ 
					System.out.println(s);
					confusionSet.add(s);
				}
			}
			allConfusionSet.put(oovAdj, confusionSet);
		}
		return allConfusionSet;
	}

	//metodo che restituisce il confusion set relativo alle parole IV
	public Map<String,List<String>> createConfusionSetIV(String tweet){
		Map<String,List<String>> allConfusionSet = new HashMap<String,List<String>>();
		ArrayList<String> confusionSet;
		LevenshteinDistance lev = new LevenshteinDistance();
		int characterEditDistance = 0;
		int phoneticEditDistance = 0;
		DoubleMetaphone d = new DoubleMetaphone();
		Number2String n = new Number2String();
		System.out.println("------Confusion set for "+tweet+"------");
		confusionSet = new ArrayList<String>();
		Set<String> dict = dictionary.keySet();
		for(String s : dict){
			characterEditDistance = lev.levenshteinDistance(n.number2String(tweet),s);
			String phonOov = d.doubleMetaphone(n.number2String(tweet));
			String phonIv = d.doubleMetaphone(n.number2String(s));
			phoneticEditDistance = lev.levenshteinDistance(phonOov, phonIv);

			if(characterEditDistance <= 2 || phoneticEditDistance <= 1){ 
				confusionSet.add(s);
			}
		}
		allConfusionSet.put(tweet, confusionSet);
		return allConfusionSet;
	}



}

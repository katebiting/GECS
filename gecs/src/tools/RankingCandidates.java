package tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class RankingCandidates {

	public static TreeMap<String, Double> sortMapByValue(HashMap<String, Double> map){
		Comparator<String> comparator = new ValueComparator(map);
		//TreeMap is a map sorted by its keys. 
		//The comparator is used to sort the TreeMap by keys. 
		TreeMap<String, Double> result = new TreeMap<String, Double>(comparator);
		result.putAll(map);
		return result;
	}
	public static Map<String,Double> rank(List<String> candidates, String oov){

		LevenshteinDistance ld = new LevenshteinDistance();
		HashMap<String,Double> map = new HashMap<String,Double>();
		DoubleMetaphone d = new DoubleMetaphone();
		Number2String n = new Number2String();
		ConfusionSetGenerator c = new ConfusionSetGenerator();
		String oovAdj = c.deleteRepetition(n.number2String(oov));

		if(candidates!=null)
		for(String s : candidates){
			double editDistance = ld.levenshteinDistance(n.number2String(oovAdj),s);
			String phonOov = d.doubleMetaphone(n.number2String(oovAdj));
			String phonIv = d.doubleMetaphone(n.number2String(s));
			double phoneticEditDistance = ld.levenshteinDistance(phonOov, phonIv);	
			double longest = LongestCommonSubsequence.lcs(s, oovAdj);
			double den ;
			double score = 0.0;

			//prefix
			if(s.startsWith(oovAdj))
				score +=1;
			//suffix
			if(s.endsWith(oovAdj))
				score +=1;
			//lcs
			if(oovAdj.length()>s.length())
				den = oovAdj.length();
			else
				den = s.length();
			score += longest/den;


			//edit
			if(editDistance == 0)
				score += 1;
			else
			score+= editDistance/(Math.exp(editDistance));
			//phonEdit
			if(phoneticEditDistance == 0)
				score += 1;
			else
			score+= phoneticEditDistance/(Math.exp(phoneticEditDistance));

			map.put(s,score);
		}
		TreeMap<String, Double> sortedMap = sortMapByValue(map);
			
		return sortedMap;
	}


	public static Map<String,Double> rank(List<String> candidates, String oov, String tweet) throws IOException{
		HashMap<String,Double> map = new HashMap<String,Double>();

		
		
		//-------//
		LevenshteinDistance ld = new LevenshteinDistance();
		DoubleMetaphone d = new DoubleMetaphone();
		Number2String n = new Number2String();
		ConfusionSetGenerator c = new ConfusionSetGenerator();
		String oovAdj = c.deleteRepetition(n.number2String(oov));


		for(String s : candidates){

			double editDistance = ld.levenshteinDistance(n.number2String(oovAdj),s);
			String phonOov = d.doubleMetaphone(n.number2String(oovAdj));
			String phonIv = d.doubleMetaphone(n.number2String(s));
			double phoneticEditDistance = ld.levenshteinDistance(phonOov, phonIv);	
			double longest = LongestCommonSubsequence.lcs(s, oovAdj);
			double den ;
			double score = 0.0;

			//prefix
			if(s.startsWith(oovAdj))
				score +=1;
			//suffix
			if(s.endsWith(oovAdj))
				score +=1;
			//lcs
			if(oovAdj.length()>s.length())
				den = oovAdj.length();
			else
				den = s.length();
			score += longest/den;

			//edit
			if(editDistance == 0)
				score += 1;
			else
			score+= editDistance/(Math.exp(editDistance));
			//phonEdit
			if(phoneticEditDistance == 0)
				score += 1;
			else
			score+= phoneticEditDistance/(Math.exp(phoneticEditDistance));
			map.put(s,score);
		}
		TreeMap<String, Double> sortedMap = sortMapByValue(map);

		return sortedMap;
	}

}

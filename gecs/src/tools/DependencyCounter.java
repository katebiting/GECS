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


public class DependencyCounter {
	
	public static TreeMap<String, Double> sortMapByValue(HashMap<String, Double> map){
		Comparator<String> comparator = new ValueComparator(map);
		//TreeMap is a map sorted by its keys. 
		//The comparator is used to sort the TreeMap by keys. 
		TreeMap<String, Double> result = new TreeMap<String, Double>(comparator);
		result.putAll(map);
		return result;
	}

	public static Map<String,Double> count(String oov,String tweet,Map<String,Double> confusion) throws IOException{
		//---Context Inference---//
		FileInputStream fis = new FileInputStream("dependencies3.arff");
		//Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		Parser p = new Parser();
		HashMap<String,Double> map = new HashMap<String,Double>();
		double num1;
		List<String> candidates = new ArrayList<String> (confusion.keySet());

		List<String> a = p.getDependencies(tweet, null);
		String line = null;
		double den1 = 1*candidates.size();
		while ((line = br.readLine()) != null) {
			for(String c : candidates){
				num1 = 1;//initialized with the smoothing factor
				for(String s : a){
					String app2 = null;
					String [] app = s.split(",");
					if(app[0].equals(oov))
						 app2= c+","+app[1]+","+app[2]+","+app[3]; 
					else if (app[1].equals(oov))
						 app2= app[0]+","+c+","+app[2]+","+app[3]; 
						if(app2!=null && app2.toLowerCase().equals(line.toLowerCase())){
							den1++;
							num1++;
					}
				}
				map.put(c,num1);

			}
		}
		for (String h : candidates){
			double valore = map.get(h)/den1+confusion.get(h);
			map.put(h, valore);
		}
		TreeMap<String, Double> sortedMap = sortMapByValue(map);

		br.close();
		return sortedMap;
	}
}

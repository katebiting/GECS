package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
public class Starter {
	public static void main(String [] args) throws Exception{

		ConfusionSetGenerator c = new ConfusionSetGenerator();
		Map<String,List<String>> d = new HashMap<String,List<String>>();
		List<String> oov = new ArrayList<>();
		System.out.println("Enter a tweet");
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		String line=buffer.readLine();
		buffer.close();
		Parser pa = new Parser();
		List<String> sa = pa.getDependencies(line);

		System.out.println("dep:" +sa);

		ArrayList<String> list = new ArrayList<>();
		String[] tw =line.split(" ");
		for(String s:tw){
			s = s.toLowerCase();
			s = s.replace(",", "");
			s = s.replace(".", "");
			s = s.replace(";", "");
			s = s.replace(":", "");
			s = s.replace("'", "");
			s = s.replace("?", "");
			s = s.replace("!", "");
			list.add(s);
		}
		System.out.println("------Loading dictionary, please wait...------");

		System.out.println("DICTIONARY LOADED");
		oov = c.oovDetector(list);

		d = c.createConfusionSet(oov);
		HashMap<String, ArrayList<String>> app = new HashMap<>();
		Map ok = null;
		String p = "";

		for(String o : oov){
			for(String s : sa){
				String[] split = s.split(",");
				if(split[0].equals(o) || split[1].contains(o)){
					System.out.println("");
					System.out.println("OOV: "+o);
					ok = RankingCandidates.rank(d.get(o),o);
					System.out.println(split[0]);
					Iterator it = ok.entrySet().iterator();
					int count = 0;
					System.out.println("----- Ranking Candidates -----");
					if(!app.containsKey(o)){
						ArrayList<String> dep = new ArrayList<>();
						while(it.hasNext()){
							count++;
							Map.Entry entry = (Map.Entry)it.next();
							System.out.println(entry.getKey()+" : "+entry.getValue());
							String corr = entry.getKey().toString();

							if(split[0].equals(o))
								//writer.println(""+corr+","+split[1]+","+split[2]+","+split[3]);
								dep.add(corr+","+split[1]+","+split[2]+","+split[3]);
							else 
								//.println(""+split[0]+","+corr+","+split[2]+","+split[3]);
								dep.add(split[0]+","+corr+","+split[2]+","+split[3]);
							if(count > 6)
								break;
						}		
						app.put(o, dep);
					}
					else{
						while(it.hasNext()){
							count++;
							Map.Entry entry = (Map.Entry)it.next();
							System.out.println(entry.getKey()+" : "+entry.getValue());
							String corr = entry.getKey().toString();

							if(split[0].equals(o))
								app.get(o).add(corr+","+split[1]+","+split[2]+","+split[3]);
							else 
								app.get(o).add(split[0]+","+corr+","+split[2]+","+split[3]);
							if(count > 6)
								break;
						}
					}
					System.out.println(app);
				}
			}
		}
		for(String key : app.keySet()){
			PrintWriter writer = new PrintWriter("tweet.arff", "UTF-8");
			writer.println("@relation Rel");
			writer.println("");
			writer.println("@attribute 1parola string");
			writer.println("@attribute 2parola string");
			writer.println("@attribute 3distanza numeric");
			writer.println("@attribute label {1,-1}");
			writer.println("");
			writer.println("@data");
			for(String l : app.get(key))
				writer.println(""+l);

			writer.close();
			System.out.println("---- Classification OOV: "+ key +" ----");
			Classifier classifier = new Classifier();
			classifier.evaluation(new File("tweet.arff"));
		}

	}
}


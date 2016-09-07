package tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;



//classe per creare le istanze negative
public class NegativeCandidates {
	private static ArrayList<String> ver = new ArrayList<>();
	private static HashMap<String, String> app = new HashMap<>();
	public static void main(String[]args) throws Exception{

		Attribute Attribute1 = new Attribute("1parola", (FastVector)null);
		Attribute Attribute2 = new Attribute("2parola", (FastVector)null);
		Attribute Attribute3 = new Attribute("3distanza", 0); 
		FastVector label = new FastVector(1);
		label.addElement("-1");

		Attribute ClassAttribute = new Attribute("label",label);
		FastVector fvWekaAttributes = new FastVector(4);
		fvWekaAttributes.addElement(Attribute1);
		fvWekaAttributes.addElement(Attribute2);
		fvWekaAttributes.addElement(Attribute3);
		fvWekaAttributes.addElement(ClassAttribute);

		Instances isTrainingSet = new Instances("Rel", fvWekaAttributes, 2);

		isTrainingSet.setClassIndex(2);
		Instance iExample = new Instance(4);
		BufferedReader reader = new BufferedReader(new FileReader("positive.arff"));
		Instances data = new Instances(reader);
		HashMap<String, Map<String,Double>> mem = new HashMap<>();
		reader.close();
		//System.out.println(data);
		String[] s = data.toString().split("@");
		String[] s1 = s[6].split("\n");
		ConfusionSetGenerator c = new ConfusionSetGenerator();
		for(int i=0; i< s1.length; i++){
			//System.out.println(s1[i]);
			ArrayList<String> iv = new ArrayList<>();
			if(s1[i].contains(",") && !ver.contains(s1[i])){
				String[] s2 = s1[i].split(",");
				iv.add(s2[0].toLowerCase());
				iv.add(s2[1].toLowerCase());
				ver.add(s1[i]);			
				Map<String, Double> rank = new HashMap<>();
				System.out.println(iv);
				if(Integer.parseInt(s2[2])== 3){
					for(String in : iv){
						if(!mem.containsKey(in)){
							rank = RankingCandidates.rank(c.createConfusionSetIV(in).get(in.toLowerCase()), in.toLowerCase());
							mem.put(in, rank);
						}
						else{
							rank = mem.get(in);
						}
						Iterator it = rank.entrySet().iterator();
						int count = 0;
						while(it.hasNext()){
							count++;
							System.out.println(count);
							Map.Entry entry = (Map.Entry)it.next();
							if(count > 6)
								break;
							System.out.println(entry.getKey()+" : "+entry.getValue());
							if(!entry.getValue().toString().equals("5.0")){
								if(in.equals(s2[0])){
									iExample.setValue((Attribute)fvWekaAttributes.elementAt(0), entry.getKey().toString());
									iExample.setValue((Attribute)fvWekaAttributes.elementAt(1), s2[1]);
									iExample.setValue((Attribute)fvWekaAttributes.elementAt(2), Integer.parseInt(s2[2]));
									iExample.setValue((Attribute)fvWekaAttributes.elementAt(3), "-1");
									isTrainingSet.add(iExample);
								}
								else if(in.equals(s2[1])){
									iExample.setValue((Attribute)fvWekaAttributes.elementAt(0), s2[0]);
									iExample.setValue((Attribute)fvWekaAttributes.elementAt(1), entry.getKey().toString());
									iExample.setValue((Attribute)fvWekaAttributes.elementAt(2), Integer.parseInt(s2[2]));
									iExample.setValue((Attribute)fvWekaAttributes.elementAt(3), "-1");
									isTrainingSet.add(iExample);
								}
							}
						}
					}
				}
			}
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter("negative1.arff"));
		writer.write(isTrainingSet.toString());
		writer.flush();
		writer.close();


	}
}

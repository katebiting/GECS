import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import tools.*;
public class Run {
	public static void main(String [] args) throws IOException{
		Map<String,List<String>> d = new HashMap<String,List<String>>();

		System.out.println("╭━━━┳━━━┳━━━┳━━━╮");
		System.out.println("┃╭━╮┃╭━━┫╭━╮┃╭━╮┃");
		System.out.println("┃┃╱╰┫╰━━┫┃╱╰┫╰━━╮");
		System.out.println("┃┃╭━┫╭━━┫┃╱╭╋━━╮┃");
		System.out.println("┃╰┻━┃╰━━┫╰━╯┃╰━╯┃");
		System.out.println("╰━━━┻━━━┻━━━┻━━━╯");
		System.out.println("");
		System.out.println("GRAMMAR ERROR CORRECTION SYSTEM");
		System.out.println("");
		System.out.println("Welcome to our correction system!\n");
		System.out.println("Loading dictonary, please wait ");
		System.out.println("");

		Timer t = new Timer();
		t.schedule(new TimerTask() {
		    @Override
		    public void run() {
		       System.out.print("░");
		    }
		}, 0, 1000);
		
		ConfusionSetGenerator c = new ConfusionSetGenerator();
		t.cancel();
		System.out.println("100%\n");
		while(true){
			String nt="";

			BufferedReader buffer;
			buffer=new BufferedReader(new InputStreamReader(System.in));

			
				System.out.println("Please type your tweet:");

				String line2 = buffer.readLine();
				ArrayList<String> list = new ArrayList<>();
				if(!line2.equals("")){
				String[] tw =line2.split(" ");
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
				List<String> oov = c.oovDetector(list);
				d = c.createConfusionSet(oov);
				if(d.size()!=0){
					System.out.println("\n------Ready for context inference and candidates selection, Please wait------");	

					Map ok = new TreeMap();
					for(String s: oov ){
					 ok = RankingCandidates.rank(d.get(s),s,line2);
					 Map snd = new TreeMap<String,Double>();
					 Iterator it2 = ok.entrySet().iterator();
					 int i = 0;
					 while (it2.hasNext()&&i<11){
						 i++;
							Map.Entry entry = (Map.Entry)it2.next();

						 snd.put(entry.getKey(), entry.getValue());
					 }
					 Map ok2 = DependencyCounter.count(s, line2, snd);
					 Iterator it = ok2.entrySet().iterator();

						String first;
						Map.Entry entry = (Map.Entry)it.next();
						nt = "";
						first = (String)entry.getKey();
						for(int k = 0; k < tw.length; k++){
							String app;
							app = tw[k].replace(",", "");
							app = app.replace(".", "");
							app = app.replace(";", "");
							app = app.replace(":", "");
							app = app.replace("'", "");
							app = app.replace("?", "");
							app = app.replace("!", "");
							if(app.equals(s)){
								nt+=first;
								if(tw[k].contains(","))
								nt+=",";
								if(tw[k].contains("."))
								nt+=".";
								if(tw[k].contains(";"))
								nt+=";";
								if(tw[k].contains(":"))
								nt+=":";
								if(tw[k].contains("?"))
								nt+="?";
								if(tw[k].contains("!"))
								nt+="!";
							}else
								nt+=tw[k];
							if(k!=tw.length-1)
								nt+=" ";
						}
						tw = nt.split(" ");
						line2 = nt;
					}
					
	

					System.out.println("\n-----Correct Tweet-----\n");
					System.out.println(nt);
					System.out.println("");


				}
					
					System.out.println("Do you want to try with another tweet?(Y/N)");
					String line3 = buffer.readLine();
					while(!line3.equals("Y")&&!line3.equals("y")&&!line3.equals("N")&&!line3.equals("n")){	
						System.out.println("Please try again.\nDo you want to try with another tweet?(Y/N)");
						line3 = buffer.readLine();
					}
					if(line3.equals("Y")||line3.equals("y"))
						continue;
					else if(line3.equals("N")||line3.equals("n")){
						System.out.println("See you soon!");
						break;
					}	
				}
				else
					System.out.println("Empty tweet, try again");





		}
	}
}


package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadDataSet {

	public List<String> parseTxt(String filepath) throws FileNotFoundException{
		ArrayList<String> result = new ArrayList<>();

		File folder = new File(filepath);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			Scanner scanner = new Scanner(new FileReader(filepath +"/"+listOfFiles[i].getName()));
			System.out.println(listOfFiles[i].getName());
			try {

				while (scanner.hasNextLine()){
					String line = scanner.nextLine();
					if(line.contains(".")){
						String[] arrayString = line.split("\\. ");
						for(String s : arrayString)
							result.add(s);

					}
					else
						if(!line.isEmpty())				
							result.add(line);
				}
			}
			finally {
				scanner.close();
			}
		}
		return result;
	}



}

package tools;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Demonstrates how to first use the tagger, then use the NN dependency
 * parser. Note that the parser will not work on untagged text.
 *
 * @author Jon Gauthier
 */
public class Parser  {
	public List<String> getDependencies(String tweet){
		List<String> depend = new ArrayList();
		String modelPath = DependencyParser.DEFAULT_MODEL;
		String taggerPath = "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";

		ReadDataSet doc = new ReadDataSet();
		MaxentTagger tagger = new MaxentTagger(taggerPath);
		DependencyParser parser = DependencyParser.loadFromModelFile(modelPath);

		DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(tweet));
		for (List<HasWord> sentence : tokenizer) {
			List<TaggedWord> tagged = tagger.tagSentence(sentence);
			GrammaticalStructure gs = parser.predict(tagged);
			Collection<TypedDependency> dependencies = gs.allTypedDependencies();

			for(TypedDependency d : dependencies){
				if(d.reln().toString()!="punct" && d.reln().toString()!="root"){
					String[] dep = d.toString().split(", ");
					String[] dep2 = dep[0].split("\\(");
					String[] dep3 = dep[0].split("\\(");
					dep[0]=dep2[1];
					dep[1]=dep[1].replaceAll("\\)","");
					int p = dep[0].lastIndexOf('-');
					dep2[0]= dep[0].substring(0, p);
					dep2[1]=dep[0].substring(p);
					int q = dep[1].lastIndexOf('-');
					dep3[0]= dep[1].substring(0, q);

					dep3[1]=dep[1].substring(q);

					dep3[0]=dep3[0].replaceAll(" ", "");
					int distanza = Math.abs(Integer.parseInt(dep2[1])-Integer.parseInt(dep3[1]));
					if(StringUtils.isAlphanumeric(dep2[0])&&StringUtils.isAlphanumeric(dep3[0]))
						if(distanza<=3)
							depend.add(""+dep2[0]+","+dep3[0]+","+distanza+",1");

				}
			}
		}


		return depend;
	}

	public List<String> getDependencies(String tweet,HashMap<String,List<String>> map){
		List<String> depend = new ArrayList();
		String modelPath = DependencyParser.DEFAULT_MODEL;
		String taggerPath = "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";

		ReadDataSet doc = new ReadDataSet();
		MaxentTagger tagger = new MaxentTagger(taggerPath);
		DependencyParser parser = DependencyParser.loadFromModelFile(modelPath);

		DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(tweet));
		for (List<HasWord> sentence : tokenizer) {
			List<TaggedWord> tagged = tagger.tagSentence(sentence);
			GrammaticalStructure gs = parser.predict(tagged);
			Collection<TypedDependency> dependencies = gs.allTypedDependencies();

			for(TypedDependency d : dependencies){
				System.out.println(d);
				if(d.reln().toString()!="punct" && d.reln().toString()!="root"){
					String[] dep = d.toString().split(", ");
					String[] dep2 = dep[0].split("\\(");
					String[] dep3 = dep[0].split("\\(");
					dep[0]=dep2[1];
					dep[1]=dep[1].replaceAll("\\)","");
					int p = dep[0].lastIndexOf('-');
					dep2[0]= dep[0].substring(0, p);
					dep2[1]=dep[0].substring(p);
					int q = dep[1].lastIndexOf('-');
					dep3[0]= dep[1].substring(0, q);

					dep3[1]=dep[1].substring(q);

					dep3[0]=dep3[0].replaceAll(" ", "");
					int distanza = Math.abs(Integer.parseInt(dep2[1])-Integer.parseInt(dep3[1]));
					if(StringUtils.isAlphanumeric(dep2[0])&&StringUtils.isAlphanumeric(dep3[0]))
						if(distanza<=3)
							depend.add(""+dep2[0]+","+dep3[0]+","+distanza+",1");

				}
			}
		}


		return depend;
	}

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		String modelPath = DependencyParser.DEFAULT_MODEL;
		String taggerPath = "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";

		for (int argIndex = 0; argIndex < args.length; ) {
			switch (args[argIndex]) {
			case "-tagger":
				taggerPath = args[argIndex + 1];
				argIndex += 2;
				break;
			case "-model":
				modelPath = args[argIndex + 1];
				argIndex += 2;
				break;
			default:
				throw new RuntimeException("Unknown argument " + args[argIndex]);
			}
		}
		List<String> prova = new ArrayList<String>();
		String e = "ciao come-stai";
		prova.add(e);
		ReadDataSet doc = new ReadDataSet();
		MaxentTagger tagger = new MaxentTagger(taggerPath);
		DependencyParser parser = DependencyParser.loadFromModelFile(modelPath);

		PrintWriter writer = new PrintWriter("dependencies3.arff", "UTF-8");
		writer.println("@relation Rel");
		writer.println("");
		writer.println("@attribute 1parola string");
		writer.println("@attribute 2parola string");
		writer.println("@attribute 3distanza numeric");
		writer.println("@attribute label {1,-1}");
		writer.println("");
		writer.println("@data");
		for(String s : doc.parseTxt("/Users/caterina/gecs/doc")){ //qui va messo il path dei file da analizzare

			DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(s));
			for (List<HasWord> sentence : tokenizer) {
				List<TaggedWord> tagged = tagger.tagSentence(sentence);
				GrammaticalStructure gs = parser.predict(tagged);
				Collection<TypedDependency> dependencies = gs.allTypedDependencies();

				for(TypedDependency d : dependencies){
					if(d.reln().toString()!="punct" && d.reln().toString()!="root"){
						System.out.println(d);
						String[] dep = d.toString().split(", ");
						String[] dep2 = dep[0].split("\\(");
						String[] dep3 = dep[0].split("\\(");
						dep[0]=dep2[1];
						dep[1]=dep[1].replaceAll("\\)","");
						int p = dep[0].lastIndexOf('-');
						dep2[0]= dep[0].substring(0, p);
						dep2[1]=dep[0].substring(p);
						int q = dep[1].lastIndexOf('-');
						dep3[0]= dep[1].substring(0, q);

						dep3[1]=dep[1].substring(q);

						dep3[0]=dep3[0].replaceAll(" ", "");
						int distanza = Math.abs(Integer.parseInt(dep2[1])-Integer.parseInt(dep3[1]));
						if(StringUtils.isAlphanumeric(dep2[0])&&StringUtils.isAlphanumeric(dep3[0]))
							if(distanza<=3)
								writer.println(""+dep2[0]+","+dep3[0]+","+distanza+",1");

					}
				}
			}
		}
		writer.close();
	}
}
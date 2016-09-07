package tools;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.trees.J48;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Range;
import weka.core.SelectedTag;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToBinary;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.StringToWordVector;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
public class Classifier {
	public void evaluation(File file) throws Exception{
		//i seguenti passaggi vengono effettuati per creare il training e il testing set
		//1)legge il file arff creato con la classe attTest (trainingTest)
		BufferedReader reader = new BufferedReader(
				new FileReader("dependencies.arff"));
		Instances data = new Instances(reader);
		reader.close();
		// aggiunge la label al file(ossia la categoria di ogni istanza)
		data.setClassIndex(3);
		//filtro per trasformare stringhe o numeri in dati nominali
		StringToWordVector filter = new StringToWordVector();
		String[] options = new String[2];
		String[] options3 = new String[1];
		String[] options4 = new String[2];
		options[0] = "-R";                                    
		options[1] = "1-2"; 
		options3[0] = "-L";
		options4[0] = "-W"; 
		options4[1] = "500000"; 
		filter.setInputFormat(data);
		filter.setOptions(options);
		//filter.setOptions(options3);
		filter.setOptions(options4);
		//dati trasformati
		System.out.println("--- Loading Training Set ---");
		Instances trainData = Filter.useFilter(data, filter);
		NumericToNominal filter2 = new NumericToNominal();
		String[] options2 = new String[2];
		options2[0] = "-R";                                    
		options2[1] = "3"; 
		filter2.setOptions(options2);
		filter2.setInputFormat(trainData);
		Instances trainingData = Filter.useFilter(trainData, filter2);
		System.out.println("load");
		//2)legge il file arff creato con la classe attTest (testingTest)
		BufferedReader readerTest = new BufferedReader(
				new FileReader(file));
		Instances dataTest = new Instances(readerTest);
		readerTest.close();
		// aggiunge la label al file(ossia la categoria di ogni istanza)
		dataTest.setClassIndex(3);
		//filtro per trasformare stringhe in dati nominali
		StringToWordVector filter3 = new StringToWordVector();
		filter3.setInputFormat(dataTest);
		//dati trasformati
		System.out.println("");
		System.out.println("--- Loading Testing Set ---");
		Instances testData = Filter.useFilter(dataTest, filter);
		NumericToBinary filter4 = new NumericToBinary();
		filter4.setInputFormat(testData);
		Instances testingData = Filter.useFilter(testData, filter2);
		System.out.println("load");
		

		System.out.println("Classification: ");
		LibSVM svm = new LibSVM();
		//1 fase di train
		svm.setKernelType(new SelectedTag(LibSVM.KERNELTYPE_LINEAR, LibSVM.TAGS_KERNELTYPE));
		svm.buildClassifier(trainingData);

		//2 fase di test
		Evaluation eval = new Evaluation(trainingData);
		eval.evaluateModel(svm, testingData);

		System.out.println(eval.toSummaryString());
		System.out.println(eval.toMatrixString());			


	}
}
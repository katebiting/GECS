package tools;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.functions.LibSVM;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToBinary;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.StringToNominal;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class Demo {

	public static void main(String[]args) throws Exception{
		//i seguenti passaggi vengono effettuati per creare il training e il testing set
		//1)legge il file arff creato con la classe attTest (trainingTest)
		BufferedReader reader = new BufferedReader(
				new FileReader("negative.arff"));
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
		filter.setOptions(options3);
		filter.setOptions(options4);

		//dati trasformati
		Instances trainData = Filter.useFilter(data, filter);
		//NumericToBinary filter2 = new NumericToBinary();
		NumericToNominal filter2 = new NumericToNominal();

		String[] options2 = new String[2];
		options2[0] = "-R";                                    
		options2[1] = "3"; 
		filter2.setOptions(options2);
		filter2.setInputFormat(trainData);
		Instances trainingData = Filter.useFilter(trainData, filter2);
		System.out.println("Training Set:");
		//System.out.println(trainingData);

		//2)legge il file arff creato con la classe attTest (testingTest)
		BufferedReader readerTest = new BufferedReader(
				new FileReader("test.arff"));
		Instances dataTest = new Instances(readerTest);
		readerTest.close();

		// aggiunge la label al file(ossia la categoria di ogni istanza)
		dataTest.setClassIndex(3);

		//filtro per trasformare stringhe in dati nominali
		StringToWordVector filter3 = new StringToWordVector();

		filter3.setInputFormat(dataTest);
		//dati trasformati
		Instances testData = Filter.useFilter(dataTest, filter);
		//NumericToBinary filter4 = new NumericToBinary();
		NumericToNominal filter4 = new NumericToNominal();
		filter4.setInputFormat(testData);
		Instances testingData = Filter.useFilter(testData, filter2);
		System.out.println("");
		System.out.println("Testing Set:");
		System.out.println(testingData);



		//classificatore svm
		/*LibSVM svm = new LibSVM();
		
		//1 fase di train
		svm.setKernelType(new SelectedTag(LibSVM.KERNELTYPE_LINEAR, LibSVM.TAGS_KERNELTYPE));

		svm.buildClassifier(trainingData);
		 ObjectOutputStream oos = new ObjectOutputStream(
                 new FileOutputStream("svm3.model"));
oos.writeObject(svm);
oos.flush();
oos.close();*/

ObjectInputStream ois = new ObjectInputStream(
        new FileInputStream("svm3.model"));
Classifier cls = (Classifier) ois.readObject();
ois.close();
		//2 fase di test(va creato ovviamente un testingSet diverso dal training, si presuppone che se si usa il training la precisione sia massima)
		Evaluation eval = new Evaluation(trainingData);
		eval.evaluateModel(cls, testingData);
		System.out.println("Corretti:");
		
		System.out.println(eval.toSummaryString());

		System.out.println(eval.toMatrixString());
		
	}
}

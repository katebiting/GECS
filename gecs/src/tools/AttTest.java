package tools;

import java.io.BufferedWriter;
import java.io.FileWriter;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Generates a little ARFF file with different attribute types.
 *
 * @author FracPete
 */
public class AttTest {
  public static void main(String[] args) throws Exception {
	  
	/* vecchio modo di costruire i dati, lo tengo, non si sa mai 
    FastVector      atts;
    Instances       data;
    double[]        vals;


    // 1. set up attributes
    atts = new FastVector();
    // - numeric
    atts.addElement(new Attribute("1"));
    // - string
    atts.addElement(new Attribute("2", (FastVector) null));



    // 2. create Instances object
    data = new Instances("MyRelation", atts, 0);

    // 3. fill with data
    // first instance
    vals = new double[data.numAttributes()];
    // - numeric
    vals[0] = 1;
    // - string
    vals[1] = data.attribute(1).addStringValue("This is a string!");

    // add
    data.add(new Instance(1.0, vals));

    // second instance
    vals = new double[data.numAttributes()];  // important: needs NEW array!
    // - numeric
    vals[0] = 1;

    // - string
    vals[1] = data.attribute(1).addStringValue("And another one!");

    // - relational

    // add
    data.add(new Instance(1.0, vals));*/

    //costruisco struttura dati
    Attribute Attribute1 = new Attribute("1", (FastVector)null);
    Attribute Attribute2 = new Attribute("2", (FastVector)null);
    FastVector label = new FastVector(1);
    label.addElement("1");

    
    
    Attribute ClassAttribute = new Attribute("label",label);
    FastVector fvWekaAttributes = new FastVector(3);
    fvWekaAttributes.addElement(Attribute1);
    fvWekaAttributes.addElement(Attribute2);
    fvWekaAttributes.addElement(ClassAttribute);
    
    Instances isTrainingSet = new Instances("Rel", fvWekaAttributes, 2);
    
    isTrainingSet.setClassIndex(2);
    
    //inizializzo istanze

    Instance iExample = new Instance(3);
    iExample.setValue((Attribute)fvWekaAttributes.elementAt(0), "ciao");
    iExample.setValue((Attribute)fvWekaAttributes.elementAt(1), "miao");

    iExample.setValue((Attribute)fvWekaAttributes.elementAt(2), "1");
    isTrainingSet.add(iExample);
    Instance iExample2 = new Instance(3);
    iExample.setValue((Attribute)fvWekaAttributes.elementAt(0), "ciao");
    iExample.setValue((Attribute)fvWekaAttributes.elementAt(1), "miao");
    iExample.setValue((Attribute)fvWekaAttributes.elementAt(2), "1");
    isTrainingSet.add(iExample);
    Instance iExample3 = new Instance(3);
    iExample.setValue((Attribute)fvWekaAttributes.elementAt(0), "ciao");
    iExample.setValue((Attribute)fvWekaAttributes.elementAt(1), "miao");
    iExample.setValue((Attribute)fvWekaAttributes.elementAt(2), "1");

    isTrainingSet.add(iExample);

    
    // 4. output data
    BufferedWriter writer = new BufferedWriter(new FileWriter("test2.arff"));
    writer.write(isTrainingSet.toString());
    writer.flush();
    writer.close();
  }
}
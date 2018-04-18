package OntologyConstruct;

import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.iterator.ExtendedIterator;

import java.io.*;

//import static org.apache.jena.tdb.index.bplustree.BPlusTreeParams.NS;

//import org.apache.jena.ontology.Individual;
//import org.apache.jena.util.iterator.ExtendedIterator;
public class RawTxt2Owl {
    private static String NS = "http://www.w3.org/2002/07/owl#";
    OntModel m;
    ObjectProperty hasbinyin;
    ObjectProperty haszhenzhuang;
    ObjectProperty hastizheng;
    ObjectProperty hasteshujiancha;
    ObjectProperty haszhiliao;
    private void fun(String leftClassName,String rightClassName,String relationName,String[] words) {
        OntClass leftClass = m.getOntClass(NS+leftClassName);
        OntClass rightClass = m.getOntClass(NS+rightClassName);
        boolean leftIsIn = false;
        boolean rightIsIn = false;
        OntClass left = null;
        OntClass right = null;
        for (ExtendedIterator<OntClass> it = leftClass.listSubClasses(); it.hasNext();) {
            String label = (left = it.next()).toString();
            if(label.equals(NS + words[0])) {
                leftIsIn = true;
                break;
            }
        }
        for(ExtendedIterator<OntClass> it = rightClass.listSubClasses(); it.hasNext();) {
            String label = (right = it.next()).toString();
            if(label.equals(NS + words[2])) {
                rightIsIn = true;
                break;
            }
        }
        if(!leftIsIn) {
            //add it to leftclass
            left = m.createClass(NS+words[0]);
            left.setLabel(words[0],"CN");
            leftClass.addSubClass(left);
        }
        if(!rightIsIn) {
            //add it to rightclass
            right = m.createClass(NS+words[2]);
            left.setLabel(words[2],"CN");
            rightClass.addSubClass(right);
        }
        ObjectProperty relation = m.createObjectProperty( NS + relationName );
        relation.addLabel(relationName,"en");
        relation.addDomain(left);
        relation.addRange(right);
    }
    private void line2owl(String line) {
        String[] words = line.split("/");
        if(words.length == 3) {
            if(words[1].equals("病因")) {
                fun("疾病类","病因类","hasEtiology",words);
            }
            else if(words[1].equals("症状")) {
                fun("疾病类","症状类","hasSymptom",words);
            }
            else if(words[1].equals("体征")) {
                fun("疾病类","体征类","hasSigns",words);
            }
            else if(words[1].equals("特殊检查")) {
                fun("疾病类","特殊检查类","hasCheck",words);
            }
            else if(words[1].equals("治疗")) {
                fun("治疗方法类","疾病类","treat",words);
            }
            else {
                //todo
            }
        }
        else {
            //todo error
        }
    }
    public void init() {
        m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
        m.createClass(NS + "疾病类");
        m.createClass(NS + "病因类");
        m.createClass(NS + "症状类");
        m.createClass(NS + "体征类");
        m.createClass(NS + "特殊检查类");
        m.createClass(NS + "治疗方法类");
    }
    public void process(String txt,OutputStream outPut) {
        String[] lines = txt.split("\n");
        for(int i = 0; i < lines.length;i++) {
            line2owl(lines[i]);
        }
        m.write(outPut);
    }
    public void process(BufferedReader br,OutputStream outPut) throws IOException {
        String line = null;
        while((line = br.readLine()) != null) {
            line2owl(line);
        }
        m.write(outPut);
    }
    public static void main(String[] argv) {
        RawTxt2Owl rto = new RawTxt2Owl();
        rto.init();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File("E:\\IdeaProjects\\JenaDemo\\Parser_repair.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileOutputStream fop = null;
        try {
            fop = new FileOutputStream(new File("E:\\IdeaProjects\\JenaDemo\\Parser_repair.rdf"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            rto.process(br,fop);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fop.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

//import org.apache.jena.ontology.*;
//import org.apache.jena.rdf.model.*;
//import org.apache.jena.util.iterator.ExtendedIterator;
//
//
//import java.io.*;
//
//
//
//public class JenaDemo {
//    private static String filePath = "file:F:\\Protege-5.2.0\\otonalg\\DIAB_v90d.owl";
//
//        private static FileReader fr;
//
//    static {
//        try {
//            fr = new FileReader(new File("F:\\Protege-5.2.0\\otonalg\\wordscnDIAB.txt"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static BufferedReader br = new BufferedReader(fr);
//
//        private static FileOutputStream out;
//
//    static {
//        try {
//            out = new FileOutputStream(
//                    new File("F:\\Protege-5.2.0\\otonalg\\words.txt"));
//        } catch (FileNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//
//    public static void main(String[] args) {
//        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
//        m.write(System.out);
//        try {
//            m.read(filePath);
//        } catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//        String cnWord;
//        for (ExtendedIterator<OntClass> it = m.listClasses(); it.hasNext();) {
//            OntClass tmpIt;
//            tmpIt = it.next();
//            try {
//                String label = tmpIt.getLabel(null);
//                if(label!=null) {
//                    cnWord = br.readLine();
//                    tmpIt.setLabel(cnWord,null);
//                    System.out.println(tmpIt.getLabel(null));
//                }
//            } catch (IOException e) {
//               System.out.println(e.getMessage());
//            }
//        }
//        FileOutputStream fileOs = null;
//        try {
//            fileOs  =
//                    new FileOutputStream(new File("F:\\Protege-5.2.0\\otonalg\\newDIAB.owl"));
//        }catch(Exception e){
//            System.out.println(e.getMessage());
//            System.exit(-1);
//        }
//        m.write(fileOs,"RDF/XML",null);
//    }
//}

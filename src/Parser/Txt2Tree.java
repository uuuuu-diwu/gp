package Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
public class Txt2Tree {
    private String filePath;
    private File file;
    private ArrayList<Node> treeVec;
    public static void readOffsetRest(BufferedReader br) {
        try {
            br.reset();
        }catch (IOException e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public Txt2Tree(String filePath) {
        file = new File(filePath);
        treeVec = new ArrayList<Node>();
    }
    public ArrayList<Node> getTreeVec() {
        return treeVec;
    }
    public void process() throws IOException{
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null) {
            if(RgexPatern.chapterPattern.matcher(line).find()) {
                String[] tmp = line.split("\\s+");
                Node chapter = new Node(tmp[tmp.length - 1]);
                System.out.println("insert chapter node " + chapter.getTitle() + " to treeVec");
                Node disaseNode = null;
                while((line = br.readLine()) != null /*&& RgexPatern.disasePattern.matcher(line).find()*/) {
                    Matcher mat = RgexPatern.disasePattern.matcher(line);
                    if(mat.find()) {
                        String disase = mat.group(1);
                        disaseNode = new Node(disase);
                        chapter.insertChild(disaseNode);
                        System.out.println("insert disase node " + disaseNode.getTitle() + " to chapter " + chapter.getTitle());
                        processDiseaseNode(disaseNode,br);
                    }
                }
                treeVec.add(chapter);
            }
        }
    }

    public void processDiseaseNode(Node diseaseNode,BufferedReader br) {
        if(diseaseNode == null)
            return;
        String line;
        while((line = markAndReadLine(br)) != null) {
            if(RgexPatern.chapterPattern.matcher(line).find()
            ||RgexPatern.disasePattern.matcher(line).find()) {
                readOffsetRest(br);
                return;
            }
            Matcher mat = RgexPatern.knowledgePointPattern.matcher(line);
            if(mat.find()) {
                    String kp = mat.group(1);
//                    Node kpNode = new Node(kp);
//                    diseaseNode.insertChild(kpNode);
                    System.out.printf("%s inserted into %s and constructing %s node\n",kp,diseaseNode.getTitle(),kp);
                    if(kp.equals("病因")) {
                        Node etiology = EtiologyParser.constructEtiology(br);
                        etiology.setParent(diseaseNode);
                        diseaseNode.insertChild(etiology);//现在疾病有了病因
                    }
                    else if(kp.equals("诊断要点")) {
                        Node diagnosis = DiagnosisKp.constructDiagnosis(br);
                        diagnosis.setParent(diseaseNode);
                        diseaseNode.insertChild(diagnosis);
                    }
                    else if(kp.equals("治疗")) {
                        Node treatment = TreatmentParser.constructTreatment(br);
                        treatment.setParent(diseaseNode);
                        diseaseNode.insertChild(treatment);
                    }
            }
        }
    }

    public void processVocabNode(Node kp,BufferedReader br) {
        String line;
        while((line = markAndReadLine(br)) != null) {
            if(RgexPatern.knowledgePointPattern.matcher(line).find()||
                    RgexPatern.chapterPattern.matcher(line).find()) {
                readOffsetRest(br);
                return;
            }
            Vocab vocab = new Vocab(line,kp);
            kp.insertVocab(vocab);
            System.out.println("voca:" + line + "inserted into" + kp.getParent().getTitle() + " --> " + kp.getTitle());
        }
    }

    public static String markAndReadLine(BufferedReader br) {
        try {
            br.mark(1024 * 4);
        }catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        try {
            return br.readLine();
        }catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return null;
    }
    public static void main(String[] argv) {
        Txt2Tree obj = new Txt2Tree("C:\\Users\\Administrator\\Desktop\\参考文献\\new_reference\\心血管病诊疗指南.txt");
        try {
            obj.process();
        }catch(IOException e) {
            System.out.println(e.getMessage());
        }
        ArrayList<Node> treeVec = obj.getTreeVec();
        Tree2Relation t2r = new Tree2Relation("C:\\Users\\Administrator\\Desktop\\参考文献\\new_reference\\心血管病诊疗指南Parser.txt",treeVec);
        t2r.process();
    }
}

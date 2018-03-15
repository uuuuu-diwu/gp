package Parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Tree2Relation {
    private BufferedWriter brw_;
    private String outPath_;
    private ArrayList<Node> treeVec_;
    public Tree2Relation(String path,ArrayList<Node> treeVec) {
        treeVec_ = treeVec;
        outPath_ = path;
        try {
            brw_ = new BufferedWriter(new FileWriter(new File(path)));
        } catch (IOException e) {
            System.err.println();
        }
    }
    private void etiologyProcess(String disaseStr,Node etiologyNode) {
        for(Iterator<Node> it = etiologyNode.getChildren().iterator();it.hasNext();) {
            Node tmp = it.next();
            String line2Write = disaseStr + "(disase)/" + "病因/" + tmp.getTitle();
            try {
                brw_.write(line2Write + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void diagnosisKpProcess(String disaseStr,Node diagnosiKpNode) {
        for(Iterator<Node> it = diagnosiKpNode.getChildren().iterator();it.hasNext();) {
            Node tmp = it.next();
            String relation = tmp.getTitle();
            for(Iterator<Node> jt = tmp.getChildren().iterator();jt.hasNext();) {
                Node subTmp = jt.next();
                String line2Write = disaseStr + "(disase)/" + tmp.getTitle() + "/"+subTmp.getTitle();
                try {
                    brw_.write(line2Write + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void treatmentProcess(String disaseStr,Node treatmentNode) {
        for(Iterator<Node> it = treatmentNode.getChildren().iterator();it.hasNext();) {
            Node tmp = it.next();
            if(tmp.getTitle().equals("药物治疗")) {
                for(Iterator<Node> jt = tmp.getChildren().iterator();jt.hasNext();) {
                    Node medicine = jt.next();
                    String line2Write = medicine.getTitle() + "(medicine)/治疗/" + disaseStr;
                    try {
                        brw_.write(line2Write + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if(tmp.getTitle().equals("外科治疗")) {
                //todo
            }
            else {
                //todo
            }
        }
    }
    public void process() {
        for(Iterator<Node> root = treeVec_.iterator();root.hasNext();) {
            Node chapterNode = root.next();
            for(Iterator<Node> disaseIt = chapterNode.getChildren().iterator();disaseIt.hasNext();) {
                Node disaseNode = disaseIt.next();
                try {
                    brw_.write(disaseNode.getTitle() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ArrayList<Node> children = disaseNode.getChildren();
                String disaseStr = disaseNode.getTitle();
                for(Iterator<Node> it = children.iterator();it.hasNext();) {
                    Node tmp = it.next();
                    if(tmp.getTitle().equals("病因")) {
                        etiologyProcess(disaseStr,tmp);
                    }
                    else if(tmp.getTitle().equals("诊断要点")) {
                        diagnosisKpProcess(disaseStr,tmp);
                    }
                    else if(tmp.getTitle().equals("治疗")) {
                        treatmentProcess(disaseStr,tmp);
                    }
                    else {
                        //todo
                    }
                }
            }
        }
        try {
            brw_.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

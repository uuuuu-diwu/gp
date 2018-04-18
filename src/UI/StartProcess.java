package UI;
import Parser.Tree2Relation;
import Parser.Txt2Tree;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StartProcess extends JButton implements ActionListener {
    private String txt_;
    private JTextArea ta = null;
    private FileSelector fs_;
    public StartProcess(FileSelector fs,JTextArea ta) {
        super("startProcess");
        this.ta = ta;
        this.fs_ =fs;
        addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        //txt_ = ta.getText();
        Txt2Tree t2t = new Txt2Tree(fs_.getSelectedFile());
        try {
            t2t.process();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Tree2Relation t2r = new Tree2Relation(t2t.getTreeVec());
        t2r.process();
        this.ta.append(t2r.getTxt().toString());
    }

    public String getTxt() {
        return txt_;
    }
}

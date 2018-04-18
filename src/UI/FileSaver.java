package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver extends JButton implements ActionListener {
    private File selectedFile = null;
    private JTextArea ta_;
    public FileSaver(JTextArea ta) {
        super("保存");
        ta_ = ta;
        addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        File file = new File("resultAfterConfirm.txt");
        BufferedWriter br = null;
        try {
            br = new BufferedWriter(new FileWriter(file));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if(br != null) {
            try {
                System.out.println(ta_.getText());
                br.write(ta_.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        try {
            br.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

}


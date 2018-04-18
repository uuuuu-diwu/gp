package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainUI extends JFrame implements ActionListener {
    public MainUI(String title) {
        super(title);
    }

    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JFileChooser jfc=new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
        jfc.showDialog(new JLabel(), "选择");
        File file=jfc.getSelectedFile();
        if(file.isDirectory()){
            System.out.println("文件夹:"+file.getAbsolutePath());
        }else if(file.isFile()){
            System.out.println("文件:"+file.getAbsolutePath());
        }
        System.out.println(jfc.getSelectedFile().getName());

    }
    public static void main(String[] argv) {
        /*new com*/
        FileSelector fileSelector = new FileSelector();
        fileSelector.setSize(100,100);
        JTextArea ta = new JTextArea(30,7);
        ta.setLineWrap(true);
       // ta.setColumns(10);
        //ta.setBounds(50,80,120,120);
        MainUI mu = new MainUI("本体生成辅助系统");
        /*mu set*/
        mu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mu.setSize(1500,1000);
        mu.setBackground(Color.BLUE);
        mu.setLayout(new BorderLayout());
        /*mu add com*/
        //JPanel panUp = new JPanel();
        //panUp.add(ta);
        //mu.add("South",fileSelector);
        mu.add("North",new JScrollPane(ta));
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,3,100,0));
        buttons.add(fileSelector);
        buttons.add(new StartProcess(fileSelector,ta));
        buttons.add(new FileSaver(ta));
        mu.add("South",buttons);
        mu.setVisible(true);
    }
}

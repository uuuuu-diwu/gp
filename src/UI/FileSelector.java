package UI;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class FileSelector extends JButton implements ActionListener {
    private File selectedFile = null;
    public FileSelector(){
        super("浏览");
        addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc=new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
        jfc.showDialog(new JLabel(), "选择");
        File file=jfc.getSelectedFile();
        if(file.isDirectory()){
            System.out.println("文件夹:"+file.getAbsolutePath());
        }else if(file.isFile()){
            System.out.println("文件:"+file.getAbsolutePath());
            selectedFile = file;
        }
        System.out.println(jfc.getSelectedFile().getName());

    }
    public File getSelectedFile() {
        return selectedFile;
    }

}
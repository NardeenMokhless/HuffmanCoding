package Huffman;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

/**
 * Created by Nardeen on 24-Oct-17.
 */
public class GHuff extends CHuff{
    private JPanel mypanel1;
    private JTextField textField1;
    private JButton button1;

    public GHuff() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = textField1.getText();
                File myFile = new File("D:\\FCI\\3.1\\Multimedia\\Assignments\\S.Huffman\\huffFile.txt");
                PrintWriter pf = null;
                try {
                    pf = new PrintWriter(myFile);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                pf.println(s);
                pf.close();


                //File myFile = new File("D:\\FCI\\3.1\\Multimedia\\Assignments\\S.Huffman\\huffFile.txt");
                Scanner sc = null;
                try {
                    sc = new Scanner(myFile);
                } catch (FileNotFoundException eex) {
                    eex.printStackTrace();
                }
                String input = sc.nextLine();
                System.out.println(input);
                sc.close();


                String comp = compress(input);
                System.out.println(comp);
                File compFile = new File("D:\\FCI\\3.1\\Multimedia\\Assignments\\S.Huffman\\hullComp.txt");
                PrintWriter pr = null;
                try {
                    pr = new PrintWriter(compFile);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                pr.println(comp);
                pr.close();

                String decomp = decompress(comp);
                System.out.println(decomp);
                File decompFile = new File("D:\\FCI\\3.1\\Multimedia\\Assignments\\S.Huffman\\hullDecomp.txt");
                PrintWriter prrr = null;
                try {
                    prrr = new PrintWriter(decompFile);
                } catch (FileNotFoundException ee) {
                    ee.printStackTrace();
                }
                prrr.println(decomp);
                prrr.close();

                for(node n : dic)
                {
                    System.out.println(n.getLetter()+"  "+n.getCode());
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new GHuff().mypanel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

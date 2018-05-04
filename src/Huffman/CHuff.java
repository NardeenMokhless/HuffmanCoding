package Huffman;

//import com.company.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by Nardeen on 24-Oct-17.
 */
public class CHuff
{
    public  static Vector<node> dic = new Vector<node>();

    public static class node implements Comparable<node>
    {
        private int freq;
        private char letter;
        private String code;

        private node left;
        private node right;

        @Override
        public int compareTo(node n)
        {
            return n.getFreq() - freq ;
        }
        public node()
        {
            freq = 0;
            letter = '*';
            code = "";
        }
        public node(int f,char l)
        {
            freq = f;
            letter = l;
            code="";
        }
        public node(int f,char l ,String c)
        {
            freq = f;
            letter = l;
            code=c;
        }
        public void setFreq(int f)
        {
            freq = f;
        }
        public void setLetter(char l)
        {
            letter = l;
        }
        public void setCode(String c)
        {
            code = c;
        }
        public void setLeft(node l)
        {
            left = l;
        }
        public void setRight(node r)
        {
            right = r;
        }
        public int getFreq()
        {
            return freq;
        }
        public char getLetter()
        {
            return letter;
        }
        public String getCode()
        {
            return code;
        }
        public node getLeft()
        {
            return left;
        }
        public node getRight()
        {
            return right;
        }
    }
    public static void SortNodes(Vector<node> v)
    {
        Collections.sort(v);
    }
    public static int searchForNode(char c,Vector<node> l)
    {
        int ind = -1;
        for(int i=0;i<l.size();i++)
            if(l.elementAt(i).getLetter()==c)
                ind = i;
        return ind;
    }
    public static int searchForCode(String s ,Vector<node> l)
    {
        int indx = -1;
        for(int i=0;i<l.size();i++)
            if(l.elementAt(i).getCode().equals(s))
                indx = i;
        return indx;
    }
    public static Vector<node> calFreq(String input)
    {
        Vector<node> l = new Vector<node>();
        node n ;         int ind = -1;

        for(int i=0;i<input.length();i++)
        {
            if(searchForNode(input.charAt(i),l)!=-1)
            {
                ind = searchForNode(input.charAt(i),l);
                int f = l.elementAt(ind).getFreq();
                l.elementAt(ind).setFreq(f+1);
            }
            else {
                n = new node(1, input.charAt(i));
                l.add(n);
            }
        }
        return l;
    }
    public static void tree (Vector<node> l)
    {
        node n;
        SortNodes(l);
        if(l.size()==1)
        {
            n = new node(l.firstElement().getFreq(),'*');
            n.setRight(l.firstElement());
            l.removeElement(l.firstElement());
            l.addElement(n);
        }
        while (l.size()!= 1)
        {
            n = new node(l.lastElement().getFreq(),'*') ;
            n.setRight(l.lastElement());
            l.removeElementAt(l.size()-1);
            int rg = n.getFreq(),lf = l.lastElement().getFreq();
            n.setFreq(rg+lf);
            n.setLeft(l.lastElement());
            l.removeElementAt(l.size()-1);
            l.addElement(n);
            SortNodes(l);
        }
    }
    public static void Coder(node par, String s)
    {
        String x = par.getCode();
        x += s;
        par.setCode(x);
        node n = new node(par.getFreq(),par.getLetter(),x);
        if(par.getLetter() != '*')dic.add(n);
        if(par.getRight() != null)Coder(par.getRight(),x+"1");
        if(par.getLeft() != null)Coder(par.getLeft(),x+"0");
    }
    public static void PreOrder(node parent)
    {
        System.out.println(parent.getFreq() +" ::  "+parent.getLetter()+" --> "+parent.getCode());
        if (parent.getLeft() != null)
            PreOrder(parent.getLeft());
        if (parent.getRight() != null)
            PreOrder(parent.getRight());
    }
    public static String compress(String input)
    {
        String comp = new String();

        Vector<node> l = new Vector<node>();
        l=calFreq(input);
        tree(l);
        Coder(l.elementAt(0),"");
        //PreOrder(l.elementAt(0));
        for(int i =0 ;i<input.length();i++)
        {
            int ind = searchForNode(input.charAt(i),dic);
            comp += dic.elementAt(ind).getCode();
        }
        return comp;
    }
    public static String decompress(String comp)
    {
        String decomp = new String(),sub = new String();

        for(int i=0;i<comp.length();i++)
        {
            sub = comp.substring(i,i+1);
            if(searchForCode(sub,dic) != -1)
                decomp += dic.elementAt(searchForCode(sub,dic)).getLetter();
            else
            {
                int start = i,end = i+1;  int count =0;
                if(end>comp.length())  sub = comp.substring(start);
                else sub=comp.substring(start,end);

                while (searchForCode(sub,dic) == -1)
                {
                    end++;
                    if(end>comp.length()) sub = comp.substring(start);
                    else sub=comp.substring(start,end);
                }
                decomp += dic.elementAt(searchForCode(sub,dic)).getLetter();
                i = end-1;
            }
            //System.out.println(i +" -- decomp = "+decomp);
        }
        return decomp;
    }
    /*
    public static void main(String[] args)
    {
        File myFile = new File("D:\\FCI\\3.1\\Multimedia\\Assignments\\S.Huffman\\huffFile.txt");
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

        for(Main.node n : dic)
        {
            System.out.println(n.getLetter()+"  "+n.getCode());
        }
        /*
         JFrame frame = new JFrame("App");
        frame.setContentPane(new LzW().myPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }*/
}

package com.client2;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
public class ChatRoom extends JPanel{
       JPanel p1=new JPanel();
       JPanel p2=new JPanel();
       JPanel p3=new JPanel();
       JPanel p4=new JPanel();
       JPanel p5=new JPanel();
       JPanel p6=new JPanel();
       
       JTextField t1=new JTextField();
       JTextField t2=new JTextField();
       JTextField t3=new JTextField();
       JTextField t4=new JTextField();
       JTextField t5=new JTextField();
       JTextField t6=new JTextField();
       
       JPanel[] pan={p1,p2,p3,p4,p5,p6};
       JTextField[] idtf={t1,t2,t3,t4,t5,t6};
       boolean[] sw=new boolean[6];
       
       JTextArea ta;
       JTextField tf;
       JTable table;
       DefaultTableModel model;
       JButton b1,b2,b3;
       
       public ChatRoom()
       {
    	   ta=new JTextArea();
    	   JScrollPane js1=new JScrollPane(ta);
    	   tf=new JTextField();
    	   
    	   b1=new JButton("강퇴");
    	   b2=new JButton("초대");
    	   b3=new JButton("나가기");
    	   
    	   String[] col={"아이디","성별"};
    	   String[][] row=new String[0][2];
    	   model=new DefaultTableModel(row,col);
    	   table=new JTable(model);
    	   JScrollPane js2=new JScrollPane(table);
    	   
    	   setLayout(null);
    	   
    	   for(int i=0;i<3;i++)
    	   {
    		   pan[i].setBackground(Color.black);
    		   pan[i].setBounds(10+(i*160), 15, 150, 150);
    		   idtf[i].setBounds(10+(i*160),170 ,150, 30);
    		   add(pan[i]);
    		   add(idtf[i]);
    	   }
    	   int k=0;
    	   for(int i=3;i<6;i++)
    	   {
    		   pan[i].setBackground(Color.black);
    		   pan[i].setBounds(10+(k*160), 210, 150, 150);
    		   idtf[i].setBounds(10+(k*160),365 ,150, 30);
    		   add(pan[i]);
    		   add(idtf[i]);
    		   k++;
    	   }
    	   
    	   js1.setBounds(10, 400, 470 , 130);
    	   add(js1);
    	   tf.setBounds(10, 535, 470, 20);
    	   add(tf);
    	   
    	   js2.setBounds(485, 15, 300, 380);
    	   add(js2);
    	   
    	   JPanel p=new JPanel();
    	   p.add(b1);
    	   p.add(b2);
    	   p.add(b3);
    	   
    	   p.setLayout(new GridLayout(3,1,5,5));
    	   p.setBounds(485, 400, 300, 150);
    	   add(p);
       }
       
       
}







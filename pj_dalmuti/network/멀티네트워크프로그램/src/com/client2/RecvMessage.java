package com.client2;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class RecvMessage extends JFrame{
    JLabel la;
    JTextField tf;
    JTextArea ta;
    JButton b1,b2;
    public RecvMessage()
    {
    	la=new JLabel("���� ���");
    	tf=new JTextField(15);
    	tf.setEnabled(false);
    	ta=new JTextArea();
    	ta.setEditable(false);
    	JScrollPane js=new JScrollPane(ta);
    	b1=new JButton("����");
    	b2=new JButton("���");
    	
    	JPanel p1=new JPanel();
    	p1.add(la);p1.add(tf);
    	JPanel p2=new JPanel();
    	p2.add(b1);
    	p2.add(b2);
    	
    	add("North",p1);
    	add("Center",js);
    	add("South",p2);
    	
    	setSize(350, 380);
    	//setVisible(true);
    }
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
       new RecvMessage();
	}
*/
}
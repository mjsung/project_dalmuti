package com.client2;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class SendMessage extends JFrame{
    JLabel la;
    JTextField tf;
    JTextArea ta;
    JButton b1,b2;
    public SendMessage()
    {
    	la=new JLabel("받는 사람");
    	tf=new JTextField(15);
    	tf.setEnabled(false);
    	ta=new JTextArea();
    	JScrollPane js=new JScrollPane(ta);
    	b1=new JButton("보내기");
    	b2=new JButton("취소");
    	
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
       new SendMessage();
	}
*/
}

package com.client2;
import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
public class InfoView extends JFrame implements ActionListener{
    JLabel la1,la2,la3,la4,la5;
    JButton b;
    public InfoView()
    {
    	la1=new JLabel("");
    	la2=new JLabel("ID:");
    	la3=new JLabel("대화명:");
    	la4=new JLabel("성별:");
    	la5=new JLabel("위치:");
    	b=new JButton("확인");
    	setLayout(null);
    	la1.setBounds(10, 15, 100, 180);
    	JPanel p=new JPanel();
    	p.setLayout(new GridLayout(4,1,3,3));
    	p.add(la2);
    	p.add(la3);
    	p.add(la4);
    	p.add(la5);
    	
    	p.setBounds(115, 15, 200, 180);
    	
    	JPanel p2=new JPanel();
    	p2.add(b);
    	
    	p2.setBounds(10, 200, 265, 35);
    	add(la1);
    	add(p);
    	add(p2);
    	
    	setSize(290,280);
    	//setVisible(true);
    	
    	b.addActionListener(this);
    }
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
       new InfoView();
	}*/
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b)
		{
			setVisible(false);
		}
	}

}




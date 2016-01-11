package com.client2;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Login extends JPanel implements ActionListener{
	JLabel la1,la2,la3;
	JTextField tf1,tf2;
	JRadioButton man,woman;
	
	JLabel av1,av2,av3;
	JRadioButton rb1,rb2,rb3;
	JButton b1,b2;
	public Login()
	{
		la1=new JLabel("아이디");
		la2=new JLabel("이  름");
		la3=new JLabel("성  별");
		
		tf1=new JTextField();
		tf2=new JTextField();
		
		man=new JRadioButton("남자");
		woman=new JRadioButton("여자");
		//라디오버튼은 반드시 그룹화 시켜줘야한다.
		ButtonGroup bg=new ButtonGroup();
		bg.add(man);
		bg.add(woman);

		ImageIcon i1=new ImageIcon("c:\\image\\m1.gif");
		ImageIcon i2=new ImageIcon("c:\\image\\m2.gif");
		ImageIcon i3=new ImageIcon("c:\\image\\m3.gif");
		
		av1=new JLabel(i1);
		av2=new JLabel(i2);
		av3=new JLabel(i3);
		
		rb1=new JRadioButton("");
		rb2=new JRadioButton("");
		rb3=new JRadioButton("");
		
		ButtonGroup bg1=new ButtonGroup();
		bg1.add(rb1);
		bg1.add(rb2);
		bg1.add(rb3);
		
		b1=new JButton("Login");
		b2=new JButton("Cancel");
		
		//배치
		
		setLayout(null);
		la1.setBounds(10, 15, 50, 20);
		tf1.setBounds(65, 15, 150, 20);
		
		la2.setBounds(10, 40, 50, 20);
		tf2.setBounds(65, 40, 150, 20);
		
		la3.setBounds(10, 65, 50, 20);
		man.setBounds(65, 65, 70, 20);
		woman.setBounds(140, 65, 70, 20);
		
		av1.setBounds(10, 90, 65, 50);
		av2.setBounds(80, 90, 65, 50);
		av3.setBounds(150, 90, 65, 50);
		
		rb1.setBounds(30, 145, 15, 15);
		rb2.setBounds(100, 145, 15, 15);
		rb3.setBounds(170, 145, 15, 15);
		
		JPanel p=new JPanel();
		p.add(b1);
		p.add(b2);
		
		p.setBounds(10, 165, 205, 35);
	
		
		add(la1); add(tf1);
		add(la2); add(tf2);
		add(la3); add(man);add(woman);
		add(av1); add(av2);add(av3);
		add(rb1); add(rb2);add(rb3);
		add(p);
        
		man.addActionListener(this);
		woman.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	   if(e.getSource()==man)
	   {
		    ImageIcon i1=new ImageIcon("c:\\image\\m1.gif");
			ImageIcon i2=new ImageIcon("c:\\image\\m2.gif");
			ImageIcon i3=new ImageIcon("c:\\image\\m3.gif");
			av1.setIcon(i1);
			av2.setIcon(i2);
			av3.setIcon(i3);
	   }
	   if(e.getSource()==woman)
	   {
		    ImageIcon i1=new ImageIcon("c:\\image\\w1.gif");
			ImageIcon i2=new ImageIcon("c:\\image\\w2.gif");
			ImageIcon i3=new ImageIcon("c:\\image\\w3.gif");
			av1.setIcon(i1);
			av2.setIcon(i2);
			av3.setIcon(i3);
	   }
	}
}






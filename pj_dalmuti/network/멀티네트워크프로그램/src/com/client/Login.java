package com.client;
import javax.swing.*;
import java.awt.*;
public class Login { 
	JFrame frame;
	
	JLabel la1,la2,la3;
	JTextField tf1,tf2;
	JRadioButton man,woman;
	
	JLabel av1,av2,av3;
	JRadioButton rb1,rb2,rb3;
	JButton b1,b2;
	
	void init()
	{
		frame=new JFrame();
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

		ImageIcon i1=new ImageIcon("c:\\img\\m1.gif");
		ImageIcon i2=new ImageIcon("c:\\img\\m2.gif");
		ImageIcon i3=new ImageIcon("c:\\img\\m3.gif");
		
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
		
		frame.setLayout(null);
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
	
		
		frame.add(la1); frame.add(tf1);
		frame.add(la2); frame.add(tf2);
		frame.add(la3); frame.add(man);frame.add(woman);
		frame.add(av1); frame.add(av2);frame.add(av3);
		frame.add(rb1); frame.add(rb2);frame.add(rb3);
		frame.add(p);

		frame.setResizable(false);  //윈도우창크기 고정
		frame.setSize(240, 250);
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		Login login=new Login();
		login.init();

		
	
	}

}

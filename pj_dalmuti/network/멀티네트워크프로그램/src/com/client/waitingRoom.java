package com.client;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

public class waitingRoom {
	JFrame frame;
	JTable table1,table2;
	DefaultTableModel model1;
	DefaultTableModel model2;
	JTextArea ta;
	JComboBox box;
	JTextField tf;
	JButton b1,b2,b3,b4,b5,b6;

	
	void init()
	{
		frame=new JFrame();
		String[] col1={"방이름","공개/비공개","인원"};
		String[][] row1=new String[0][3];
		
		model1=new DefaultTableModel(row1,col1);
		table1=new JTable(model1);
		JScrollPane js1=new JScrollPane(table1);
		
		frame=new JFrame();
		String[] col2={"ID","이름","성별","위치"};
		String[][] row2=new String[0][4];
		
		model2=new DefaultTableModel(row2,col2);
		table2=new JTable(model2);
		JScrollPane js2=new JScrollPane(table2);
		
		ta=new JTextArea();
		JScrollPane js3=new JScrollPane(ta);
		tf=new JTextField(22);
		box=new JComboBox();
		box.addItem("black");
		box.addItem("red");
		box.addItem("green");
		box.addItem("blue");
		
		b1=new JButton("방만들기");
		b2=new JButton("방들어가기");
		b3=new JButton("1:1신청");
		b4=new JButton("쪽지보내기");
		b5=new JButton("정보보기");
		b6=new JButton("나가기");
		
		frame.setLayout(null);
		js1.setBounds(10, 15, 450, 300);
		js2.setBounds(10, 320, 450, 230);
		
		js3.setBounds(465, 15, 320, 260);
		
				
		JPanel p2=new JPanel();
		GridLayout g=new GridLayout(3,2,5,5);
		p2.setLayout(g);
		p2.add(b1);p2.add(b2);
		p2.add(b3);p2.add(b4);
		p2.add(b5);p2.add(b6);
		
		JPanel p1=new JPanel();
		p1.add(tf);p1.add(box);
			
		p1.setBounds(465, 280, 320, 30);
		p2.setBounds(465, 320, 320, 230);
		
		
		
		frame.add(js1);
		frame.add(js2);
		frame.add(js3);
		frame.add(p1);
		frame.add(p2);
		frame.setSize(820, 600);
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		
		waitingRoom w=new waitingRoom();
		w.init();
		

	}

}

package com.client2;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
public class Invate extends JFrame{
      JTable table;
      DefaultTableModel model;
      JButton b;
      public Invate()
      {
    	   String[] col={"ID","성별"};
    	   String[][] row =new String[0][2];
    	   model=new DefaultTableModel(row,col)
    	   {
    		   public boolean isCellEditable(int r,int c)
    		   {
    			   return false;
    		   }
    	   };
    	   table=new JTable(model);
    	   JScrollPane js=new JScrollPane(table);
    	   b=new JButton("취소");
    	   
    	   add("Center",js);
    	   add("South",b);
    	   setSize(300,350);
    	   //setVisible(true);
      }
     /* public static void main(String[] arg)
      {
    	  new Invate();
      }*/
}








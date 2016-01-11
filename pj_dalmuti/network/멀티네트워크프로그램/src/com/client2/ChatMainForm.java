package com.client2;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

// NetWork 
import java.io.*;
import java.net.*;
import java.util.*;

import com.common.*;
public class ChatMainForm extends JFrame implements ActionListener,
Runnable,MouseListener{
    Login login=new Login();
    WaitRoom wr=new WaitRoom();
    MakeRoom mr=new MakeRoom();
    ChatRoom cr=new ChatRoom();
    Invate invate=new Invate();
    InfoView info=new InfoView();
    SendMessage sm=new SendMessage();
    RecvMessage rm=new RecvMessage();
    
    CardLayout card=new CardLayout();
    
    //Network
    Socket s;
    OutputStream out;
    BufferedReader in;
    
    String myRoom,myId;
    
    int selRow=-1;
    public ChatMainForm()
    {
    	setLayout(card);
    	
    	add("LOGIN",login);
    	add("WR",wr);
    	add("CR",cr);
    	setSize(820, 600);
    	setVisible(true);
    	
    	login.b1.addActionListener(this);
    	login.b2.addActionListener(this);
    	wr.tf.addActionListener(this);
    	wr.b1.addActionListener(this);
    	
    	//�游��� 
    	mr.b1.addActionListener(this);
    	mr.b2.addActionListener(this);
    	
    	wr.table1.addMouseListener(this);
    	
    	//�濡�� ä�� 
    	cr.tf.addActionListener(this);
    	
    	cr.b1.addActionListener(this);//����
    	cr.b2.addActionListener(this);//�ʴ�
    	cr.b3.addActionListener(this);//������
    	wr.b6.addActionListener(this);
    	
    	wr.table2.addMouseListener(this);
    	
    	//�ʴ��ϱ�
    	invate.table.addMouseListener(this);
    	invate.b.addActionListener(this);
    	
    	//����������
    	wr.b4.addActionListener(this);
    	wr.b5.addActionListener(this);
    	//��������
    	sm.b1.addActionListener(this);
    	sm.b2.addActionListener(this);
    	rm.b1.addActionListener(this);
    	rm.b2.addActionListener(this);
    	
    	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    	addWindowListener(new WindowAdapter() {
    		 public void windowClosing(WindowEvent e)
    		 {
    			 JOptionPane.showMessageDialog(ChatMainForm.this, "������ ��ư�� Ŭ���ϼ���!!");
    		 }
		});
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 try {


		        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

		    }  catch (Exception e) { }

	


        new ChatMainForm();
	}
	public void connection(String id,String name,String sex,int avata)
	{
		  try
		  {
			   s=new Socket("localhost",3355);
			   in=new BufferedReader(new InputStreamReader(s.getInputStream()));
			   out=s.getOutputStream();
			   
			   out.write((Function.LOGIN+"|"+id
					   +"|"+name+"|"+sex+"|"+avata+"\n").getBytes());
		  }catch(Exception ex){}
		  
		  //��� ����
		  new Thread(this).start();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==login.b1)
		{
			String id=login.tf1.getText().trim();
			if(id.length()<1)
			{
				 JOptionPane.showMessageDialog(this, "ID�Է�");
				 login.tf1.requestFocus();
				 return;
			}
			// [nickname]: 
			String name=login.tf2.getText().trim();
			if(name.length()<1)
			{
				 JOptionPane.showMessageDialog(this, "��ȭ���Է�");
				 login.tf2.requestFocus();
				 return;
			}
			
			String sex="";
			if(login.man.isSelected())
			{
				 sex="����";
			}
			else
			{
				sex="����";
			}
			
			int avata=0;
			if(login.rb1.isSelected())
				avata=1;
			else if(login.rb2.isSelected())
				avata=2;
			else 
				avata=3;
			
			connection(id, name, sex, avata);
		}
		else if(e.getSource()==wr.tf)
		{
			String msg=wr.tf.getText().trim();
			if(msg.length()<1)
				   return;
			try
			{
				 out.write((Function.WAITCHAT+"|"+msg+"\n").getBytes());
				 wr.tf.setText("");
			}catch(Exception ex){}
		}
		//�游��� �� 
		else if(e.getSource()==wr.b1)
		{
			mr.tf.setText("");
			mr.rb1.setSelected(true);
			mr.la3.setVisible(false);
			mr.pf.setText("");
			mr.pf.setVisible(false);
			mr.box.setSelectedIndex(0);
			mr.setVisible(true);
		}
		//�游���
		else if(e.getSource()==mr.b1)
		{
			  String rname=mr.tf.getText().trim();
			  if(rname.length()<1)
			  {
				  JOptionPane.showMessageDialog(this,"���̸��� �Է��ϼ���");
				  mr.tf.requestFocus();
				  return;
			  }
			  //�ߺ�üũ 
			  String str="";
			  for(int i=0;i<wr.model1.getRowCount();i++)
			  {
				   str=wr.model1.getValueAt(i, 0).toString();
				   if(rname.equals(str))
				   {
					   JOptionPane.showMessageDialog(this, "�̹� �����ϴ� ���Դϴ�");
					   mr.tf.setText("");
					   mr.tf.requestFocus();
					   return;
				   }
			  }
			  String state="",pwd="";
			  if(mr.rb1.isSelected())
			  {
				  state="����";
				  pwd=" ";
			  }
			  else
			  {
				  state="�����";
				  pwd=String.valueOf(mr.pf.getPassword());//char[] ==> ���ڿ��� ��ȯ
				  //mr.pf.getText();
			  }
			  
			  int inwon=mr.box.getSelectedIndex()+2;
			  
			  //������ ���� 
			  try
			  {
				  out.write((Function.MAKEROOM+"|"+rname+"|"
			                    +state+"|"+pwd+"|"+inwon+"\n").getBytes());
			  }catch(Exception ex){}
			  
			  mr.setVisible(false);
		}
		//�游��� ���
		else if(e.getSource()==mr.b2)
		{
			mr.setVisible(false);
		}
		//�濡�� ä�� 
		else if(e.getSource()==cr.tf)
		{
			 String msg=cr.tf.getText().trim();
			 if(msg.length()<1)
				 return;
			 //������ ���� 
			 /*
			  *   ��� ã�´� ==> id (waitVc)
			  *   �濡 �ִ� ��� ==> roomName(userVc)
			  */
			 try
			 {
				 out.write((Function.ROOMCHAT+"|"+myRoom+"|"+msg+"\n").getBytes());
				 // Server ==> in.readLine() (Thread==> run())
				 /*
				  *   1. �̺�Ʈ �߻� (Button,Mouse)   
				  *      ==> ������ ��û�� ����
				  *      ******** ���������� Ŭ�� , �ּ� ���� 
				  *                 login.jsp?id=aaa&pwd=1234
				  *   2. Server (����� ����ϴ� �����忡�� ó��)
				  *      class Client
				  *      {
				  *          public void run(){}
				  *      }
				  *      ==> ��û�� ������� Ŭ���̾�Ʈ�� ���� 
				  *      ==> ������ ==> ó�� ����� Ŭ���̾�Ʈ �������� ����
				  *   3. run() : �������� ������ ������ �޾Ƽ� 
				  *       �����쿡 ��� (������ ���)
				  *       
				  *   Client ==> Server ==> Client
				  *   ����Ŭ 
				  *     ��û ==> ����� ==> ������ ����(���)
				  *     SQL  
				  *   ������
				  *     ��û (������) ==> ���Ͽ�û ==> 
				  *     ���� ã�� ==> ���ϳ��� ������ ����
				  */
			 }catch(Exception ex){}
			 cr.tf.setText("");
		}
		//�α��� ���
		else if(e.getSource()==login.b2)
		{
			 dispose();
			 System.exit(0);
		}
		//ä�� ����
		else if(e.getSource()==wr.b6)
		{
			try
			{
				 out.write((Function.CHATEND+"|\n").getBytes());
			}catch(Exception ex){}
		}
		//�� ������ 
		else if(e.getSource()==cr.b3)
		{
			try
			{
				 out.write((Function.ROOMOUT+"|"+myRoom+"\n").getBytes());
			}catch(Exception ex){}
		}
		// �ʴ� (�����)
		else if(e.getSource()==cr.b2)
		{
			for(int i=invate.model.getRowCount()-1;i>=0;i--)
				 invate.model.removeRow(i);
			
			for(int i=0;i<wr.model2.getRowCount();i++)
			{
				String id=wr.model2.getValueAt(i, 0).toString();
				String sex=wr.model2.getValueAt(i, 2).toString();
				String pos=wr.model2.getValueAt(i, 3).toString();
				if(pos.equals("����"))
				{
					String[] temp={id,sex};
					invate.model.addRow(temp);
				}
			}
			invate.setVisible(true);
		}
		else if(e.getSource()==invate.b)
		{
			invate.setVisible(false);
		}
		// ��������
		else if(e.getSource()==wr.b5)
		{
			 if(selRow==-1)
			 {
				  JOptionPane.showMessageDialog(this, "������ ����� �����ϼ���");
				  return;
			 }
			 try
			 {
				 String yid=wr.model2.getValueAt(selRow, 0).toString();
				 out.write((Function.INFO+"|"+yid+"\n").getBytes());
			 }catch(Exception ex){}
		}
		//����������
		else if(e.getSource()==wr.b4)
		{
			if(selRow==-1)
			{
				JOptionPane.showMessageDialog(this,"�������� ��� ����");
				return;
			}
			String yid=wr.model2.getValueAt(selRow, 0).toString();
			sm.tf.setText(yid);
			sm.ta.setText("");
			sm.setVisible(true);
		}
		// ������,���
		else if(e.getSource()==sm.b2)
		{
			 sm.setVisible(false);
			 selRow=-1;
		}
		else if(e.getSource()==rm.b2)
		{
			 rm.setVisible(false);
			 selRow=-1;
		}
		else if(e.getSource()==sm.b1)
		{
			String id=sm.tf.getText();
			String msg=sm.ta.getText();
			try
			{
				out.write((Function.MSGSEND+"|"+id+"|"+msg.replace('\n', '\t')+"\n").getBytes());
			}catch(Exception ex){}
			sm.setVisible(false);
		}
		else if(e.getSource()==rm.b1)
		{
			 String id=rm.tf.getText();
			 sm.ta.setText("");
			 sm.tf.setText(id);
			 rm.setVisible(false);
			 sm.setVisible(true);
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			while(true)
			{
				String msg=in.readLine();
				System.out.println(msg);
				StringTokenizer st=new StringTokenizer(msg,"|");
				int no=Integer.parseInt(st.nextToken());
				switch(no)
				{
					case Function.LOGIN:
					{
						String[] temp={
						   st.nextToken(),		
						   st.nextToken(),	
						   st.nextToken(),	
						   st.nextToken()
						};
						wr.model2.addRow(temp);
					}
					break;
					case Function.MYLOG:
					{
						 myId=st.nextToken();
						 setTitle(myId);
						 card.show(getContentPane(), "WR");
					}
					break;
					case Function.WAITCHAT:
					{
						 wr.bar.setValue(wr.bar.getMaximum());
						 wr.ta.append(st.nextToken()+"\n");
					}
					break;
					case Function.MAKEROOM:
					{
						 String[] temp={st.nextToken(),st.nextToken(),st.nextToken()};
						 wr.model1.addRow(temp);
					}
					break;
					case Function.ROOMIN:
					{
						 String id=st.nextToken();
						 String sex=st.nextToken();
						 String avata=st.nextToken();
						 myRoom=st.nextToken();
						 String s="";
						 if(sex.equals("����")) 
							 s="m";
						 else 
							 s="w";
						 
						 for(int i=0;i<6;i++)
						 {
							  if(!cr.sw[i])
							  {
								  cr.sw[i]=true;
								  cr.idtf[i].setText(id);
								  cr.pan[i].removeAll();
								  cr.pan[i].setLayout(new BorderLayout());
								  cr.pan[i].add("Center",
										  new JLabel(new ImageIcon("c:\\image\\"+s+avata+".gif")));
								  cr.pan[i].validate();//panel���ġ
								  break;
							  }
						 }
						 String[] temp={id,sex};
						 cr.model.addRow(temp);
						 card.show(getContentPane(), "CR");
					}
					break;
					case Function.REFLUSH:
					{
						 String id=st.nextToken();
						 String pos=st.nextToken();
						 String str="";
						 for(int i=0;i<wr.model2.getRowCount();i++)
						 {
							 str=wr.model2.getValueAt(i, 0).toString();
							 if(str.equals(id))
							 {
								 wr.model2.setValueAt(pos, i, 3);
								 break;
							 }
						 }
					}
					break;
					case Function.ROOMADD:
					{
						 String id=st.nextToken();
						 String sex=st.nextToken();
						 String avata=st.nextToken();
						 
						 String s="";
						 if(sex.equals("����")) 
							 s="m";
						 else 
							 s="w";
						 
						 for(int i=0;i<6;i++)
						 {
							  if(!cr.sw[i])
							  {
								  cr.sw[i]=true;
								  cr.idtf[i].setText(id);
								  cr.pan[i].removeAll();
								  cr.pan[i].setLayout(new BorderLayout());
								  cr.pan[i].add("Center",
										  new JLabel(new ImageIcon("c:\\image\\"+s+avata+".gif")));
								  cr.pan[i].validate();//panel���ġ
								  break;
							  }
						 }
						 String[] temp={id,sex};
						 cr.model.addRow(temp);
					}
					break;
					case Function.ROOMCHAT:
					{
						 cr.ta.append(st.nextToken()+"\n");
					}
					break;
					case Function.WAITROOMUPDATE:
					{
						 String id=st.nextToken();
						 String pos=st.nextToken();
						 String rname=st.nextToken();
						 String current=st.nextToken();
						 String max=st.nextToken();
						 
						 String temp="";
						 for(int i=0;i<wr.model1.getRowCount();i++)
						 {
							  temp=wr.model1.getValueAt(i, 0).toString();
							  if(temp.equals(rname))
							  {
								  if(Integer.parseInt(current)<1)
								  {
									   wr.model1.removeRow(i);
								  }
								  else
								  {
									  wr.model1.setValueAt(current+"/"+max, i, 2);
								  }
								  break;
							  }
						 }
						 for(int i=0;i<wr.model2.getRowCount();i++)
						 {
							 temp=wr.model2.getValueAt(i, 0).toString();
							 if(temp.equals(id))
							 {
								 wr.model2.setValueAt(pos, i, 3);
								 break;
							 }
						 }
					}
					break;
					case Function.CHATEND:
					{
						  String id=st.nextToken();
						  String temp="";
						  for(int i=0;i<wr.model2.getRowCount();i++)
						  {
							  temp=wr.model2.getValueAt(i, 0).toString();
							  if(id.equals(temp))
							  {
								  wr.model2.removeRow(i);
								  break;
							  }
						  }
					}
					break;
					case Function.MYCHATEND:
					{
						 dispose();
						 System.exit(0);
					}
					break;
					case Function.ROOMOUT:
					{
						String id=st.nextToken();
						for(int i=0;i<6;i++)
						 {
							String mid=cr.idtf[i].getText();
							if(mid.equals(id))
							{
								 cr.sw[i]=false;
								 cr.idtf[i].setText("");
								 cr.pan[i].removeAll();
								 cr.pan[i].setLayout(new BorderLayout());
								 cr.pan[i].add("Center",
										 new JLabel(new ImageIcon("c:\\image\\default.png")));
								 cr.pan[i].validate();
							}
						 }
						for(int i=0;i<cr.model.getRowCount();i++)
						{
							 String mid=cr.model.getValueAt(i, 0).toString();
							 if(mid.equals(id))
							 {
								 cr.model.removeRow(i);
								 break;
							 }
						}
					}
					break;
					case Function.MYROOMOUT:
					{
						 for(int i=0;i<6;i++)
						 {
							 cr.sw[i]=false;
							 cr.idtf[i].setText("");
							 cr.pan[i].removeAll();
							 cr.pan[i].setLayout(new BorderLayout());
							 cr.pan[i].add("Center",
									 new JLabel(new ImageIcon("c:\\image\\default.png")));
							 cr.pan[i].validate();
						 }
						 cr.tf.setText("");
						 cr.ta.setText("");
						 for(int i=cr.model.getRowCount()-1;i>=0;i--)
							    cr.model.removeRow(i);
						 
						 card.show(getContentPane(), "WR");
					}
					break;
					case Function.INVATE:
					{
						 String id=st.nextToken();
						 String pos=st.nextToken();
						 int a=JOptionPane.showConfirmDialog(this,
								id+"���� "+pos+"�濡 �ʴ��Ͽ����ϴ�", 
								"����",
								JOptionPane.YES_NO_OPTION);
						 if(a==JOptionPane.YES_OPTION)
						 {
						    out.write((Function.ROOMIN+"|"+pos+"\n").getBytes());
						 }
						 else
						 {
							 out.write((Function.INVATE_NO+"|"+id+"\n").getBytes());
						 }
					}
					break;
					case Function.INVATE_NO:
					{
						String id=st.nextToken();
						JOptionPane.showMessageDialog(this,
							 id+"���� �ʴ븦 �����ϼ̽��ϴ�");
					}
					break;
					case Function.INFO:
					{
						 String id=st.nextToken();
						 String name=st.nextToken();
						 String sex=st.nextToken();
						 String pos=st.nextToken();
						 String avata=st.nextToken();
						 String temp="";
						 if(sex.equals("����"))
							 temp="m";
						 else
							 temp="w";
						 info.la1.setIcon(new ImageIcon("c:\\image\\"+temp+avata+".gif"));
						 info.la2.setText("ID:"+id);
						 info.la3.setText("��ȭ��:"+name);
						 info.la4.setText("����:"+sex);
						 info.la5.setText("��ġ:"+pos);
						 info.setVisible(true);
						 selRow=-1;
					}
					break;
					case Function.MSGSEND:
					{
						 rm.ta.setText("");
						 rm.tf.setText(st.nextToken());
						 rm.ta.setText(st.nextToken().replace('\t', '\n'));
						 rm.setVisible(true);
					}
					break;
				}
			}
		}catch(Exception ex){}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==wr.table1)
		{
			if(e.getClickCount()==2)
			{
				 int row=wr.table1.getSelectedRow();
				 String rn=wr.model1.getValueAt(row, 0).toString();
				 String inwon=wr.model1.getValueAt(row, 2).toString();
				 StringTokenizer st=new StringTokenizer(inwon,"/");
				 // 6/6
				 String s1=st.nextToken();
				 String s2=st.nextToken();
				 
				 if(s1.equals(s2))
				 {
					 JOptionPane.showMessageDialog(this,"�̹� ���� á���ϴ�");
					 return;
				 }
				 
				 try
				 {
					 out.write((Function.ROOMIN+"|"+rn+"\n").getBytes());
				 }catch(Exception ex){}
			}
		}
		else if(e.getSource()==wr.table2)
		{
			int row=wr.table2.getSelectedRow();
			selRow=row;
			String id=wr.model2.getValueAt(row, 0).toString();
			if(myId.equals(id))
			{
				wr.b3.setEnabled(false);
				wr.b4.setEnabled(false);
				wr.b5.setEnabled(false);
			}
			else
			{
				wr.b3.setEnabled(true);
				wr.b4.setEnabled(true);
				wr.b5.setEnabled(true);
			}
		}
		else if(e.getSource()==invate.table)
		{
			if(e.getClickCount()==2)
			{
				int row=invate.table.getSelectedRow();
				String id=invate.model.getValueAt(row, 0).toString();
				try
				{
					 out.write((Function.INVATE+"|"+id+"\n").getBytes());
				}catch(Exception ex){}
				
				invate.setVisible(false);
			}
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}









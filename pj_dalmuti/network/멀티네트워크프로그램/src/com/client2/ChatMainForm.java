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
    	
    	//방만들기 
    	mr.b1.addActionListener(this);
    	mr.b2.addActionListener(this);
    	
    	wr.table1.addMouseListener(this);
    	
    	//방에서 채팅 
    	cr.tf.addActionListener(this);
    	
    	cr.b1.addActionListener(this);//강퇴
    	cr.b2.addActionListener(this);//초대
    	cr.b3.addActionListener(this);//나가기
    	wr.b6.addActionListener(this);
    	
    	wr.table2.addMouseListener(this);
    	
    	//초대하기
    	invate.table.addMouseListener(this);
    	invate.b.addActionListener(this);
    	
    	//쪽지보내기
    	wr.b4.addActionListener(this);
    	wr.b5.addActionListener(this);
    	//정보보기
    	sm.b1.addActionListener(this);
    	sm.b2.addActionListener(this);
    	rm.b1.addActionListener(this);
    	rm.b2.addActionListener(this);
    	
    	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    	addWindowListener(new WindowAdapter() {
    		 public void windowClosing(WindowEvent e)
    		 {
    			 JOptionPane.showMessageDialog(ChatMainForm.this, "나가기 버튼을 클릭하세요!!");
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
		  
		  //통신 시작
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
				 JOptionPane.showMessageDialog(this, "ID입력");
				 login.tf1.requestFocus();
				 return;
			}
			// [nickname]: 
			String name=login.tf2.getText().trim();
			if(name.length()<1)
			{
				 JOptionPane.showMessageDialog(this, "대화명입력");
				 login.tf2.requestFocus();
				 return;
			}
			
			String sex="";
			if(login.man.isSelected())
			{
				 sex="남자";
			}
			else
			{
				sex="여자";
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
		//방만들기 폼 
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
		//방만들기
		else if(e.getSource()==mr.b1)
		{
			  String rname=mr.tf.getText().trim();
			  if(rname.length()<1)
			  {
				  JOptionPane.showMessageDialog(this,"방이름을 입력하세요");
				  mr.tf.requestFocus();
				  return;
			  }
			  //중복체크 
			  String str="";
			  for(int i=0;i<wr.model1.getRowCount();i++)
			  {
				   str=wr.model1.getValueAt(i, 0).toString();
				   if(rname.equals(str))
				   {
					   JOptionPane.showMessageDialog(this, "이미 존재하는 방입니다");
					   mr.tf.setText("");
					   mr.tf.requestFocus();
					   return;
				   }
			  }
			  String state="",pwd="";
			  if(mr.rb1.isSelected())
			  {
				  state="공개";
				  pwd=" ";
			  }
			  else
			  {
				  state="비공개";
				  pwd=String.valueOf(mr.pf.getPassword());//char[] ==> 문자열로 변환
				  //mr.pf.getText();
			  }
			  
			  int inwon=mr.box.getSelectedIndex()+2;
			  
			  //서버로 전송 
			  try
			  {
				  out.write((Function.MAKEROOM+"|"+rname+"|"
			                    +state+"|"+pwd+"|"+inwon+"\n").getBytes());
			  }catch(Exception ex){}
			  
			  mr.setVisible(false);
		}
		//방만들기 취소
		else if(e.getSource()==mr.b2)
		{
			mr.setVisible(false);
		}
		//방에서 채팅 
		else if(e.getSource()==cr.tf)
		{
			 String msg=cr.tf.getText().trim();
			 if(msg.length()<1)
				 return;
			 //서버로 전송 
			 /*
			  *   사람 찾는다 ==> id (waitVc)
			  *   방에 있는 사람 ==> roomName(userVc)
			  */
			 try
			 {
				 out.write((Function.ROOMCHAT+"|"+myRoom+"|"+msg+"\n").getBytes());
				 // Server ==> in.readLine() (Thread==> run())
				 /*
				  *   1. 이벤트 발생 (Button,Mouse)   
				  *      ==> 서버로 요청값 전송
				  *      ******** 브라우저으로 클릭 , 주소 변경 
				  *                 login.jsp?id=aaa&pwd=1234
				  *   2. Server (통신을 담당하는 쓰레드에서 처리)
				  *      class Client
				  *      {
				  *          public void run(){}
				  *      }
				  *      ==> 요청한 결과값을 클라이언트로 전송 
				  *      ==> 웹서버 ==> 처리 결과를 클라이언트 브라우저로 전송
				  *   3. run() : 서버에서 들어오는 응답을 받아서 
				  *       윈도우에 출력 (브라우저 출력)
				  *       
				  *   Client ==> Server ==> Client
				  *   오라클 
				  *     요청 ==> 결과값 ==> 브라우저 전송(출력)
				  *     SQL  
				  *   웹서버
				  *     요청 (브라우저) ==> 파일요청 ==> 
				  *     파일 찾기 ==> 파일내용 브라우저 전송
				  */
			 }catch(Exception ex){}
			 cr.tf.setText("");
		}
		//로그인 취소
		else if(e.getSource()==login.b2)
		{
			 dispose();
			 System.exit(0);
		}
		//채팅 종료
		else if(e.getSource()==wr.b6)
		{
			try
			{
				 out.write((Function.CHATEND+"|\n").getBytes());
			}catch(Exception ex){}
		}
		//방 나가기 
		else if(e.getSource()==cr.b3)
		{
			try
			{
				 out.write((Function.ROOMOUT+"|"+myRoom+"\n").getBytes());
			}catch(Exception ex){}
		}
		// 초대 (방들어가기)
		else if(e.getSource()==cr.b2)
		{
			for(int i=invate.model.getRowCount()-1;i>=0;i--)
				 invate.model.removeRow(i);
			
			for(int i=0;i<wr.model2.getRowCount();i++)
			{
				String id=wr.model2.getValueAt(i, 0).toString();
				String sex=wr.model2.getValueAt(i, 2).toString();
				String pos=wr.model2.getValueAt(i, 3).toString();
				if(pos.equals("대기실"))
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
		// 정보보기
		else if(e.getSource()==wr.b5)
		{
			 if(selRow==-1)
			 {
				  JOptionPane.showMessageDialog(this, "정보볼 대상을 선택하세요");
				  return;
			 }
			 try
			 {
				 String yid=wr.model2.getValueAt(selRow, 0).toString();
				 out.write((Function.INFO+"|"+yid+"\n").getBytes());
			 }catch(Exception ex){}
		}
		//쪽지보내기
		else if(e.getSource()==wr.b4)
		{
			if(selRow==-1)
			{
				JOptionPane.showMessageDialog(this,"쪽지보낼 대상 선택");
				return;
			}
			String yid=wr.model2.getValueAt(selRow, 0).toString();
			sm.tf.setText(yid);
			sm.ta.setText("");
			sm.setVisible(true);
		}
		// 보내기,취소
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
						 if(sex.equals("남자")) 
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
								  cr.pan[i].validate();//panel재배치
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
						 if(sex.equals("남자")) 
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
								  cr.pan[i].validate();//panel재배치
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
								id+"님이 "+pos+"방에 초대하였습니다", 
								"선택",
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
							 id+"님이 초대를 거절하셨습니다");
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
						 if(sex.equals("남자"))
							 temp="m";
						 else
							 temp="w";
						 info.la1.setIcon(new ImageIcon("c:\\image\\"+temp+avata+".gif"));
						 info.la2.setText("ID:"+id);
						 info.la3.setText("대화명:"+name);
						 info.la4.setText("성별:"+sex);
						 info.la5.setText("위치:"+pos);
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
					 JOptionPane.showMessageDialog(this,"이미 방이 찼습니다");
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









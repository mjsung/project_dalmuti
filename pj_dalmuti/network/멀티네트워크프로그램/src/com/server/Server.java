package com.server;
import java.util.*;
import java.io.*;
import java.net.*;
import com.common.*;
public class Server implements Runnable{
    ServerSocket ss;
    Vector<Client> waitVc=new Vector<Client>();//������ ���� ����
    Vector<Room> roomVc=new Vector<Room>();//������ ���� 
    public Server()
    {
    	try
    	{
    		ss=new ServerSocket(3355);
    		System.out.println("Start Server...");
    	}catch(Exception ex){}
    }
    //������ �޴´� 
    public void run()
    {
    	  try
    	  {
    		  while(true)
    		  {
    			  Socket s=ss.accept();
    			  Client client=new Client(s);
    			  client.start();
    		  }
    	  }catch(Exception ex){}
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Server server=new Server();
        new Thread(server).start();
	}
    class Client extends Thread
    {
    	 String id,name,sex,pos;
    	 int avata;
    	 Socket s;
    	 BufferedReader in;//�б�
    	 OutputStream out;//���� TCP
    	 
    	 public Client(Socket s)
    	 {
    		  try
    		  {
    			   this.s=s;
    			   in=new BufferedReader(new InputStreamReader(s.getInputStream()));
    			   out=s.getOutputStream();
    		  }catch(Exception ex){}
    	 }
    	 //��� 
    	 public void run()
    	 {
    		   try
    		   {
    			   while(true)
    			   {
    				    //��û �޴´� 
    				   String msg=in.readLine();
    				   // 100|id|name|sex|avata
    				   System.out.println(msg);
    				   StringTokenizer st=new StringTokenizer(msg,"|");
    				   int no=Integer.parseInt(st.nextToken());
    				    //ó��
    				   switch(no)
    				   {
	    				   case Function.LOGIN:
	    				   {
	    					    id=st.nextToken();
	    					    name=st.nextToken();
	    					    sex=st.nextToken();
	    					    avata=Integer.parseInt(st.nextToken());
	    					    pos="����";
	    					    messageAll(Function.LOGIN+"|"+id+"|"+name+"|"+sex+"|"+pos);
	    					    waitVc.addElement(this);
	    					    
	    					    messageTo(Function.MYLOG+"|"+id);
	    					    for(int i=0;i<waitVc.size();i++)
	    					    {
	    					    	Client c=waitVc.elementAt(i);
	    					    	messageTo(Function.LOGIN+"|"+c.id+"|"
	    					    	           +c.name+"|"+c.sex+"|"+c.pos);
	    					    }
	    					    
	    					    //������ �������� ������
	    					    for(int i=0;i<roomVc.size();i++)
	    					    {
	    					    	Room room=roomVc.elementAt(i);
	    					    	messageTo(Function.MAKEROOM+"|"
	    					    		+room.roomName+"|"+room.roomState
	    					    		+"|"+room.current+"/"+room.maxcount);
	    					    }
	    				   }
	    				   break;
	    				   case Function.WAITCHAT:
	    				   {
	    					    String strMsg=st.nextToken();
	    					    messageAll(Function.WAITCHAT+"|["+name+"]"+strMsg);
	    				   }
	    				   break;
	    				   case Function.MAKEROOM:
	    				   {
	    					    Room room=new Room(
	    					    		st.nextToken(),
	    					    		st.nextToken(), st.nextToken(),
	    					    		Integer.parseInt(st.nextToken()));
	    					    messageAll(Function.MAKEROOM+"|"
	    					    		+room.roomName+"|"+room.roomState
	    					    		+"|"+room.current+"/"+room.maxcount);
	    					    
	    					    roomVc.addElement(room);
	    					    room.userVc.addElement(this);//����
	    					    pos=room.roomName;
	    					    // ���� ==> ���̸� ��ȯ
	    					    messageTo(Function.ROOMIN+"|"+id+"|"+sex+"|"+avata+"|"+room.roomName);
	    					    messageAll(Function.REFLUSH+"|"+id+"|"+pos);
	    					    //���ǿ��� ��ġ�� ����
	    				   }
	    				   break;
	    				   case Function.ROOMIN:
	    				   {
	    					   /*
	    					    *    1) ���̸��� �޴´� 
	    					    *    2) ���� ã�´�
	    					    *    3) ==> �����ο����� 
	    					    *        ==> ��ġ����
	    					    *     4) �濡 �ִ� ����鿡�� 
	    					    *         �������� ����
	    					    *         ����޼��� ���� 
	    					    *     5) userVc �� �߰�
	    					    *     6) �濡 �����϶�� �޼��� ���� 
	    					    *     7) �濡 �� �ִ� ��� ����� ������ �޴´�
	    					    *     8) ���� ������ �Ѵ� 
	    					    */
	    					    String rname=st.nextToken();
	    					    for(int i=0;i<roomVc.size();i++)
	    					    {
	    					    	Room room=roomVc.elementAt(i);
	    					    	if(rname.equals(room.roomName))//���� ã�´�
	    					    	{
	    					    		 room.current++;
	    					    		 pos=room.roomName;
	    					    		 // �̹� �濡 �� �ִ� ����� ó��
	    					    		 for(int j=0;j<room.userVc.size();j++)
	    					    		 {
	    					    			 Client c=room.userVc.elementAt(j);
	    					    			 c.messageTo(Function.ROOMADD+"|"+id+"|"+sex+"|"+avata);
	    					    			 c.messageTo(Function.ROOMCHAT+"|[�˸�] "+id+"���� �����ϼ̽��ϴ�");
	    					    		 }
	    					    		 // ����(�濡 ���� ���)
	    					    		 messageTo(Function.ROOMIN+"|"+id+"|"+sex+"|"+avata+"|"+room.roomName);
	    					    		 room.userVc.addElement(this);
	    					    		 for(int j=0;j<room.userVc.size();j++)
	    					    		 {
	    					    			
	    					    			 Client c=room.userVc.elementAt(j);
	    					    			 if(!id.equals(c.id))
	    					    			 {
	    					    			    messageTo(Function.ROOMADD+"|"+c.id+"|"+c.sex+"|"+c.avata);
	    					    			 }
	    					    		 }
	    					    		 //���� ó�� 
	    					    		 messageAll(Function.WAITROOMUPDATE+"|"
	    					    		          +id+"|"+pos+"|"+room.roomName+"|"
	    					    		          +room.current+"|"+room.maxcount);
	    					    		 // 6/6
	    					    	}
	    					    }
	    				   }
	    				   break;
	    				   case Function.ROOMOUT:
	    				   {
	    					   /*
	    					    *    1) ���̸��� �޴´� 
	    					    *    2) ���� ã�´�
	    					    *    3) ==> �����ο����� 
	    					    *        ==> ��ġ����(����)
	    					    *     4) �濡 �ִ� ����鿡�� 
	    					    *         �������
	    					    *         ����޼��� ���� 
	    					    *     5) userVc �� ����
	    					    *     6) ���Ƿ� ��ȯ �޼��� ���� 
	    					    *     
	    					    *     7) ���� ������ �Ѵ� 
	    					    */
	    					    String rname=st.nextToken();
	    					    for(int i=0;i<roomVc.size();i++)
	    					    {
	    					    	Room room=roomVc.elementAt(i);
	    					    	if(rname.equals(room.roomName))//���� ã�´�
	    					    	{
	    					    		 room.current--;
	    					    		 pos="����";
	    					    		 // �̹� �濡 �� �ִ� ����� ó��
	    					    		 for(int j=0;j<room.userVc.size();j++)
	    					    		 {
	    					    			 Client c=room.userVc.elementAt(j);
	    					    			 c.messageTo(Function.ROOMOUT+"|"+id);
	    					    			 c.messageTo(Function.ROOMCHAT+"|[�˸�] "+id+"���� �����ϼ̽��ϴ�");
	    					    		 }
	    					    		 // ����(�濡 ���� ���)
	    					    		 messageTo(Function.MYROOMOUT+"|");
	    					    		 
	    					    		 for(int j=0;j<room.userVc.size();j++)
	    					    		 {
	    					    			
	    					    			 Client c=room.userVc.elementAt(j);
	    					    			 if(id.equals(c.id))
	    					    			 {
	    					    			     room.userVc.removeElementAt(j);
	    					    			     break;
	    					    			 }
	    					    		 }
	    					    		 //���� ó�� 
	    					    		 messageAll(Function.WAITROOMUPDATE+"|"
	    					    		          +id+"|"+pos+"|"+room.roomName+"|"
	    					    		          +room.current+"|"+room.maxcount);
	    					    		 // 6/6
	    					    		 
	    					    		 if(room.current<1)
	    					    			 roomVc.removeElementAt(i);
	    					    	}
	    					    }
	    				   }
	    				   break;
	    				   case Function.ROOMCHAT:
	    				   {
	    					    String rname=st.nextToken();
	    					    String strMsg=st.nextToken();
	    					    for(int i=0;i<roomVc.size();i++)
	    					    {
	    					    	Room room=roomVc.elementAt(i);
	    					    	if(rname.equals(room.roomName))
	    					    	{
	    					    		 for(int j=0;j<room.userVc.size();j++)
	    					    		 {
	    					    			  Client c=room.userVc.elementAt(j);
	    					    			  c.messageTo(Function.ROOMCHAT+"|["+name+"]"+strMsg);
	    					    		 }
	    					    	}
	    					    }
	    				   }
	    				   break;
	    				   case Function.CHATEND:
	    				   {
	    					    messageAll(Function.CHATEND+"|"+id);
	    					    for(int i=0;i<waitVc.size();i++)
	    					    {
	    					    	Client c=waitVc.elementAt(i);
	    					    	if(id.equals(c.id))
	    					    	{
	    					    		messageTo(Function.MYCHATEND+"|");
	    					    		waitVc.removeElementAt(i);
	    					    		in.close();
	    					    		out.close();
	    					    	}
	    					    }
	    				   }
	    				   break;
	    				   case Function.INVATE:
	    				   {
	    					    String yid=st.nextToken();
	    					    for(int i=0;i<waitVc.size();i++)
	    					    {
	    					    	Client c=waitVc.elementAt(i);
	    					    	if(yid.equals(c.id))
	    					    	{
	    					    		c.messageTo(Function.INVATE+"|"+id+"|"+pos);
	    					    		break;
	    					    	}
	    					    }
	    				   }
	    				   break;
	    				   case Function.INVATE_NO:
	    				   {
	    					    String yid=st.nextToken();
	    					    for(int i=0;i<waitVc.size();i++)
	    					    {
	    					    	Client c=waitVc.elementAt(i);
	    					    	if(yid.equals(c.id))
	    					    	{
	    					    		c.messageTo(Function.INVATE_NO+"|"+id);
	    					    		break;
	    					    	}
	    					    }
	    				   }
	    				   break;
	    				   case Function.INFO:
	    				   {
	    					    String yid=st.nextToken();
	    					    for(int i=0;i<waitVc.size();i++)
	    					    {
	    					    	Client user=waitVc.elementAt(i);
	    					    	if(yid.equals(user.id))
	    					    	{
	    					    		messageTo(Function.INFO+"|"
	    					    	               +user.id+"|"
	    					    	               +user.name+"|"
	    					    	               +user.sex+"|"
	    					    	               +user.pos+"|"
	    					    	               +user.avata);
	    					    		break;
	    					    	}
	    					    }
	    				   }
	    				   break;
	    				   case Function.MSGSEND:
	    				   {
	    					    String yid=st.nextToken();
	    					    String strMsg=st.nextToken();
	    					    for(int i=0;i<waitVc.size();i++)
	    					    {
	    					    	Client user=waitVc.elementAt(i);
	    					    	if(yid.equals(user.id))
	    					    	{
	    					    	    user.messageTo(Function.MSGSEND+"|"+id+"|"+strMsg);
	    					    	    break;
	    					    	}
	    					    }
	    				   }
	    				   break;
    				   }
    				    //ó�� ��� ���� (����)
    			   }
    		   }catch(Exception ex){}
    	 }
    	 // ����  ( ���� , ��ü )
    	 // �ߺ����� ==> ��� ==> �޼ҵ� ==> Ŭ����
    	 public void messageTo(String str)
    	 {
    		  try
    		  {
    			   out.write((str+"\n").getBytes());
    		  }catch(Exception ex){}
    	 }
    	 public void messageAll(String str)
    	 {
    		  try
    		  {
    			   for(int i=0;i<waitVc.size();i++)
    			   {
    				   Client c=waitVc.elementAt(i);
    				   c.messageTo(str);
    			   }
    		  }catch(Exception ex){}
    	 }
    	 
    }
}









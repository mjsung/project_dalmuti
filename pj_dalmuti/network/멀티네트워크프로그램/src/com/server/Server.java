package com.server;
import java.util.*;
import java.io.*;
import java.net.*;
import com.common.*;
public class Server implements Runnable{
    ServerSocket ss;
    Vector<Client> waitVc=new Vector<Client>();//접속자 정보 저장
    Vector<Room> roomVc=new Vector<Room>();//방정보 저장 
    public Server()
    {
    	try
    	{
    		ss=new ServerSocket(3355);
    		System.out.println("Start Server...");
    	}catch(Exception ex){}
    }
    //접속을 받는다 
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
    	 BufferedReader in;//읽기
    	 OutputStream out;//쓰기 TCP
    	 
    	 public Client(Socket s)
    	 {
    		  try
    		  {
    			   this.s=s;
    			   in=new BufferedReader(new InputStreamReader(s.getInputStream()));
    			   out=s.getOutputStream();
    		  }catch(Exception ex){}
    	 }
    	 //통신 
    	 public void run()
    	 {
    		   try
    		   {
    			   while(true)
    			   {
    				    //요청 받는다 
    				   String msg=in.readLine();
    				   // 100|id|name|sex|avata
    				   System.out.println(msg);
    				   StringTokenizer st=new StringTokenizer(msg,"|");
    				   int no=Integer.parseInt(st.nextToken());
    				    //처리
    				   switch(no)
    				   {
	    				   case Function.LOGIN:
	    				   {
	    					    id=st.nextToken();
	    					    name=st.nextToken();
	    					    sex=st.nextToken();
	    					    avata=Integer.parseInt(st.nextToken());
	    					    pos="대기실";
	    					    messageAll(Function.LOGIN+"|"+id+"|"+name+"|"+sex+"|"+pos);
	    					    waitVc.addElement(this);
	    					    
	    					    messageTo(Function.MYLOG+"|"+id);
	    					    for(int i=0;i<waitVc.size();i++)
	    					    {
	    					    	Client c=waitVc.elementAt(i);
	    					    	messageTo(Function.LOGIN+"|"+c.id+"|"
	    					    	           +c.name+"|"+c.sex+"|"+c.pos);
	    					    }
	    					    
	    					    //개설된 방정보를 보낸다
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
	    					    room.userVc.addElement(this);//방장
	    					    pos=room.roomName;
	    					    // 대기실 ==> 방이름 전환
	    					    messageTo(Function.ROOMIN+"|"+id+"|"+sex+"|"+avata+"|"+room.roomName);
	    					    messageAll(Function.REFLUSH+"|"+id+"|"+pos);
	    					    //대기실에서 위치를 변경
	    				   }
	    				   break;
	    				   case Function.ROOMIN:
	    				   {
	    					   /*
	    					    *    1) 방이름을 받는다 
	    					    *    2) 방을 찾는다
	    					    *    3) ==> 현재인원증가 
	    					    *        ==> 위치변경
	    					    *     4) 방에 있는 사람들에게 
	    					    *         개인정보 전송
	    					    *         입장메세지 전송 
	    					    *     5) userVc 에 추가
	    					    *     6) 방에 입장하라는 메세지 전송 
	    					    *     7) 방에 들어가 있는 모든 사람의 정보를 받는다
	    					    *     8) 대기실 갱신을 한다 
	    					    */
	    					    String rname=st.nextToken();
	    					    for(int i=0;i<roomVc.size();i++)
	    					    {
	    					    	Room room=roomVc.elementAt(i);
	    					    	if(rname.equals(room.roomName))//방을 찾는다
	    					    	{
	    					    		 room.current++;
	    					    		 pos=room.roomName;
	    					    		 // 이미 방에 들어가 있는 사람들 처리
	    					    		 for(int j=0;j<room.userVc.size();j++)
	    					    		 {
	    					    			 Client c=room.userVc.elementAt(j);
	    					    			 c.messageTo(Function.ROOMADD+"|"+id+"|"+sex+"|"+avata);
	    					    			 c.messageTo(Function.ROOMCHAT+"|[알림] "+id+"님이 입장하셨습니다");
	    					    		 }
	    					    		 // 본인(방에 들어가는 사람)
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
	    					    		 //대기실 처리 
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
	    					    *    1) 방이름을 받는다 
	    					    *    2) 방을 찾는다
	    					    *    3) ==> 현재인원감소 
	    					    *        ==> 위치변경(대기실)
	    					    *     4) 방에 있는 사람들에게 
	    					    *         삭제명령
	    					    *         퇴장메세지 전송 
	    					    *     5) userVc 에 삭제
	    					    *     6) 대기실로 변환 메세지 전송 
	    					    *     
	    					    *     7) 대기실 갱신을 한다 
	    					    */
	    					    String rname=st.nextToken();
	    					    for(int i=0;i<roomVc.size();i++)
	    					    {
	    					    	Room room=roomVc.elementAt(i);
	    					    	if(rname.equals(room.roomName))//방을 찾는다
	    					    	{
	    					    		 room.current--;
	    					    		 pos="대기실";
	    					    		 // 이미 방에 들어가 있는 사람들 처리
	    					    		 for(int j=0;j<room.userVc.size();j++)
	    					    		 {
	    					    			 Client c=room.userVc.elementAt(j);
	    					    			 c.messageTo(Function.ROOMOUT+"|"+id);
	    					    			 c.messageTo(Function.ROOMCHAT+"|[알림] "+id+"님이 퇴장하셨습니다");
	    					    		 }
	    					    		 // 본인(방에 들어가는 사람)
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
	    					    		 //대기실 처리 
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
    				    //처리 결과 전송 (응답)
    			   }
    		   }catch(Exception ex){}
    	 }
    	 // 응답  ( 개인 , 전체 )
    	 // 중복제거 ==> 제어문 ==> 메소드 ==> 클래스
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









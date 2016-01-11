package com.server;
import java.util.*;
public class Room {
     String roomName,roomState,roomPwd;
     int maxcount;
     int current;
     Vector<Server.Client> userVc=new Vector<Server.Client>();
     public Room(String rn,String rs,String rp,int max)
     {
    	 roomName=rn;
    	 roomState=rs;
    	 roomPwd=rp;
    	 maxcount=max;
    	 current=1;
     }
}

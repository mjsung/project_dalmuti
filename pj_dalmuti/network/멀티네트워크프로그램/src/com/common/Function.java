package com.common;
// id name sex avata
// 100 aaa aaa aaa 10
public class Function {
     public static final int LOGIN=100; //로그인된 사람(대기실)
     public static final int MYLOG=110;//로그인 하는 사람 (로그인==>대기실)
     
     //방 
     public static final int MAKEROOM=200;
     public static final int ROOMIN=210;
     public static final int ROOMOUT=220;//남아 있는 사람 
     
     public static final int ROOMADD=230;//먼저 방에 들어가 있는 사람
     public static final int MYROOMOUT=240;
     
     public static final int WAITROOMUPDATE=250;
     
     public static final int REFLUSH=260;
     //채팅 
     public static final int WAITCHAT=300;
     public static final int ROOMCHAT=310;
     public static final int MTOMCHAT=320;
     
     //나가기
     //대기실 : 쪽지보내기,정보보기 
     //방 : 강퇴,초대
     public static final int INVATE=400;
     public static final int INVATE_NO=410;
     public static final int CHATEND=900;
     public static final int MYCHATEND=910;
     
     
     public static final int MSGSEND=500;//쪽지 보내기
     public static final int INFO=510;//정보보기 
}















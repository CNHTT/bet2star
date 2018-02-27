package com.bet.utils;
import org.java_websocket.WebSocket;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by Extra on 2017/8/17.
 * GitHub cnhttt@163.com
 * Work to SZFP
 */
public class ServerThread extends Thread {
    public Socket s;

    public boolean is=true;
    public ServerThread(Socket socket) {
        this.s = socket;
    }

    public void run() {
        PrintWriter pw = null;
        Calendar c;

        is =true;
        try {
            byte[] recData = null;
            InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream();
            while(is) {
                recData = new byte[1024];
                int r = in.read(recData);
                //int r = in.read(recData);
                if (r > -1) {
                    String data = new String(recData);
                    if (data.trim().equals("[OVER]")) {  //正常断开
                        s.close();
                        //socket断开之后移除本地保存的socket
                        SocketPool.removeUser(s);
                    }
                    System.out.println("读取到POS发送的来数据：" + data);

                    WebSocket webSocket = WsPool.getWsByUser("console");
                    if (webSocket!=null) WsPool.sendMessageToUser(webSocket,data.trim());

                    if (data.length()>6){

                        try {
                            Map<String,String> map = DataUtils.toMap(data.trim());
                            switch (map.get("type")){
                                case "/printer/printer":
                                    out.write("DONE".getBytes());
                                    break;
                                case  "/printer/verify":
                                    String sn = map.get("sn");
                                    SocketPool.addUser(sn,s);
//                                    out.write("[NOORDER /]".getBytes());
                                    break;
                                case  "/printer/getOrder":
//                                    out.write("[NOORDER /]".getByt/es());
                                    break;
                                case  "/printer/test":
                                   //                                    out.write("[NOORDER /]".getBytes());
                                    break;
                            }
                        }catch (Exception e){
                            out.write("[NOORDER /]".getBytes());
                        }

                    }else {
                        s.close();
                    }
//                    out.write("[NOORDER /]".getBytes());
                    } else {
                    //读取的数据为空
                    is=false;
                    System.out.println("数据读取完毕！");
                    s.close();
                    //socket断开之后移除本地保存的socket
                    SocketPool.removeUser(s);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {

        }
    }
}
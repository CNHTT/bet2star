package com.bet.utils;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Extra on 2017/8/17.
 * GitHub cnhttt@163.com
 * Work to SZFP
 */
public class SocketPool {
    private static final Map<Socket, String> wsUserMap = new HashMap<Socket, String>();

    /**
     * 向所有的用户发送消息
     *
     * @param message
     */
    public static void sendMessageToAll(String message) {
        Set<Socket> keySet = wsUserMap.keySet();

        ExecutorService service = Executors.newFixedThreadPool(50);
        synchronized (keySet) {
            for (Socket conn : keySet) {
                String user = wsUserMap.get(conn);
                if (user != null) {
                    service.submit(new SubThread(conn,message));
                }
            }
        }
    }
    static class SubThread extends Thread{
        private Socket connection;
        private String msg;
        public SubThread(Socket conSocket, String message){
            this.connection=conSocket;
            this.msg = message;
        }

        public void run(){
            try {

                writeMsgToClient(connection.getOutputStream(),msg);
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                if (connection!=null) {
//                    try {
//                        connection .close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        }

        /**
         * 读取客户端信息
         * @param inputStream
         */
        private  void readMessageFromClient(InputStream inputStream) throws IOException {
            Reader reader = new InputStreamReader(inputStream);
            BufferedReader br=new BufferedReader(reader);
            String a = null;
            while((a=br.readLine())!=null){
                System.out.println(a);
            }
        }

        /**
         * 响应客户端信息
         * @param outputStream
         * @param string
         */
        private  void writeMsgToClient(OutputStream outputStream, String string) throws IOException {
//            Writer writer = new OutputStreamWriter(outputStream);
//            writer.append(string);
//            writer.flush();
//            writer.close();
            outputStream.write(string.getBytes());
        }
    }
    /**
     * 向连接池中添加连接
     * @param adminID
     * @param conn
     */
    public static void addUser(String adminID, Socket conn) {
        wsUserMap.put(conn, adminID); // 添加连接
    }
    /**
     * 根据userName获取Socket,这是一个list,此处取第一个
     * 因为有可能多个socket对应一个userName（但一般是只有一个，因为在close方法中，我们将失效的socket连接去除了）
     *
     * @param adminID
     */
    public static Socket getWsByUser(String adminID) {
        Set<Socket> keySet = wsUserMap.keySet();
        synchronized (keySet) {
            for (Socket conn : keySet) {
                String cuser = wsUserMap.get(conn);
                if (cuser==adminID) {
                    return conn;
                }
            }
        }
        return null;
    }

    public static Map<Socket, String> getWsUserMap() {
        return wsUserMap;
    }

    /**
     * 移除连接池中的连接
     * @param conn
     * @return
     */
    public static boolean removeUser(Socket conn) {
        if (wsUserMap.containsKey(conn)) {
            wsUserMap.remove(conn); // 移除连接
            return true;
        } else {
            return false;
        }
    }

}

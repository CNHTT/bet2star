package com.bet.utils;

import org.java_websocket.WebSocket;

import java.util.*;

/**
 * Created by Extra on 2017/8/15.
 * GitHub cnhttt@163.com
 * Work to SZFP
 */
public class WsPool {
    private static final Map<WebSocket, String> wsUserMap = new HashMap<WebSocket, String>();

    /**
     * 通过websocket连接获取其对应的用户
     *
     * @param conn
     * @return
     */
    public static String getUserByWs(WebSocket conn) {
        return wsUserMap.get(conn);
    }

    /**
     * 根据userName获取WebSocket,这是一个list,此处取第一个
     * 因为有可能多个websocket对应一个userName（但一般是只有一个，因为在close方法中，我们将失效的websocket连接去除了）
     *
     * @param adminID
     */
    public static WebSocket getWsByUser(String adminID) {
        Set<WebSocket> keySet = wsUserMap.keySet();
        synchronized (keySet) {
            for (WebSocket conn : keySet) {
                String cuser = wsUserMap.get(conn);
                if (cuser.equals(adminID)) {
                    return conn;
                }
            }
        }
        return null;
    }

    /**
     * 向连接池中添加连接
     * @param adminID
     * @param conn
     */
    public static void addUser(String adminID, WebSocket conn) {
        wsUserMap.put(conn, adminID); // 添加连接
    }


    /**
     * 获取所有连接池中的用户，因为set是不允许重复的，所以可以得到无重复的user数组
     * @return
     */
    public static Collection<String> getOnlineUser() {
        List<String> setUsers = new ArrayList<String>();
        Collection<String> setUser = wsUserMap.values();
        for (String u : setUser) {
            setUsers.add(u);
        }
        return setUsers;
    }


    /**
     * 移除连接池中的连接
     * @param conn
     * @return
     */
    public static boolean removeUser(WebSocket conn) {
        if (wsUserMap.containsKey(conn)) {
            wsUserMap.remove(conn); // 移除连接
            return true;
        } else {
            return false;
        }
    }

    /**
     * 向特定的用户发送数据
     *
     * @param conn
     * @param message
     */
    public static void sendMessageToUser(WebSocket conn, String message) {
        if (null != conn && null != wsUserMap.get(conn)) {
            conn.send(message);
        }
    }

    /**
     * 向所有的用户发送消息
     *
     * @param message
     */
    public static void sendMessageToAll(String message) {
        Set<WebSocket> keySet = wsUserMap.keySet();
        synchronized (keySet) {
            for (WebSocket conn : keySet) {
                String user = wsUserMap.get(conn);
                if (user != null) {
                    conn.send(message);
                }
            }
        }
    }
}

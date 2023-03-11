package org.rainbow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        BufferedReader bi = null;
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
            Translator tr = (Translator) registry.lookup("translator");
            bi = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("请开始输入查询词：（输入 quit 退出）");
            String res;
            while ((res = bi.readLine()) != null) {
                if (res.equals("quit")) {
                    break;
                }
                res = tr.translate(res);
                System.out.println(res != null ? res : "词典中未包含该词。");
            }
        } catch (RemoteException | NotBoundException e) {
            System.err.println("未找到相应的远程翻译服务。");
//            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("输入异常，客户端即将关闭。");
//            e.printStackTrace();
        } finally {
            try {
                if (bi != null) {
                    bi.close();
                }
            } catch (IOException e) {
                System.err.println("BufferedReader 关闭异常。");
//                e.printStackTrace();
            }
        }
    }
}
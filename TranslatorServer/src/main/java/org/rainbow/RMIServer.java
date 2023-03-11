package org.rainbow;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) {
        System.out.println("正在初始化翻译服务。");
        try {
            Registry r = LocateRegistry.createRegistry(1099);
            Translator tr = new RemoteTranslator();
            r.bind("translator", tr);
            System.out.println("服务初始化完成，等待执行服务。");
        } catch (RemoteException | AlreadyBoundException e) {
            System.err.println("服务初始化失败。");
            e.printStackTrace();
        }
    }
}

package org.rainbow;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Translator extends Remote {
    /**
     * 翻译方法接口。
     *
     * @param str 输入的待翻译词汇原文。
     * @return 英汉互译后的内容，若词典中不包含此单词则返回 null。
     * @throws RemoteException 远程错误。
     */
    String translate(String str) throws RemoteException;
}

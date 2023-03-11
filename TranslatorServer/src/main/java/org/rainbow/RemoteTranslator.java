package org.rainbow;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class RemoteTranslator extends UnicastRemoteObject implements Translator {

    static Map<String, String> dictionary;

    public RemoteTranslator() throws RemoteException {
        if (dictionary == null) {
            buildDict();
        }
    }

    public static void main(String[] args) throws RemoteException {
        RemoteTranslator tr = new RemoteTranslator();
        String res = tr.translate("好");
        System.out.println(res != null ? res : "null");
    }

    private void buildDict() {
        dictionary = new HashMap<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("dic.txt"));
            String l;
            while ((l = br.readLine()) != null) {
                l = l.strip();
                if (l.length() < 3) {
                    continue;
                }
                int idx = l.indexOf(" ");
                String k = l.substring(0, idx);
                String v = l.substring(idx + 1);
                dictionary.put(k, v);
                dictionary.put(v, k);
            }
            System.out.println("词典初始化完成。");
        } catch (IOException e) {
            System.err.println("注意：词典可能为空。");
            System.err.println("当前工作路径：" + System.getProperty("user.dir"));
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                System.err.println("BufferedReader 关闭异常");
                e.printStackTrace();
            }
        }
    }

    /**
     * Translator 接口的 HashMap 形式实现，采用哈希映射的方案查找词汇。
     *
     * @param str 输入的待翻译词汇原文。
     * @return 英汉互译后的内容，若词典中不包含此单词则返回 null。
     * @throws RemoteException 远程错误。
     */
    @Override
    public String translate(String str) throws RemoteException {
        return dictionary.get(str);
    }
}
package javaIO.section20;

import java.io.RandomAccessFile;
import java.util.Arrays;

public class RandomAccessFileTest {
    public static void main(String[] args) {
        read();
    }

    public static void read() {
        try {
            RandomAccessFile rf = new RandomAccessFile("a.txt", "rw");
            // 输出默认的指针位置
            System.out.println("默认指针位置" + rf.getFilePointer());
            // 写入字符 A B
            rf.write('A');
            System.out.println("当前指针位置" + rf.getFilePointer());
            rf.write('B');
            // 写入 “中”
            String s = "中";
            rf.write(s.getBytes("gbk"));
            System.out.println("文件末尾" + rf.getFilePointer());
            // 移动指针到文件头
            rf.seek(0);
            byte[] buffer = new byte[(int)rf.length()];
            rf.read(buffer);
            System.out.println(Arrays.toString(buffer));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

package javaIO.section43;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BufferedOutputStreamTest {
    public static void main(String[] args) {
        try {
            FileOutputStream fout = new FileOutputStream("a.txt");
            BufferedOutputStream bfout = new BufferedOutputStream(fout);
            bfout.write("hello".getBytes());
            // 关闭流之前缓冲输出流会将数据全部写出
            bfout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

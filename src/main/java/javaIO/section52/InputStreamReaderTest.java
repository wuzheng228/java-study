package javaIO.section52;

import java.io.FileInputStream;
import java.io.InputStreamReader;

public class InputStreamReaderTest {
    public static void main(String[] args) {
        try {
            FileInputStream fin = new FileInputStream("a.txt");
            InputStreamReader reader = new InputStreamReader(fin, "gbk");
            char[] buf = new char[32];
            while (reader.ready()) {
                int len = reader.read(buf);
                System.out.println(new String(buf, 0, len));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

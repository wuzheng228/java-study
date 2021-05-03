package javaIO.section53;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class BufferedReaderDemo {
    public static void main(String[] args) {
        FileInputStream in;
        try {
            in = new FileInputStream("a.txt");
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader breader = new BufferedReader(reader);
            while (breader.ready()) {
                System.out.println(breader.readLine());
            }
            breader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package javaIO.section43;

import java.io.*;

public class FileCopyDemo {
    public static void main(String[] args) {
        try (FileInputStream fin = new FileInputStream("a.txt");
             FileOutputStream fout = new FileOutputStream("new2.txt");
             BufferedInputStream bfin = new BufferedInputStream(fin);
             BufferedOutputStream bfout = new BufferedOutputStream(fout)) {
            while (bfin.available() > 0) {
                int d = bfin.read();
                bfout.write(d);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

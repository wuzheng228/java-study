package javaIO.section53;

import java.io.*;

public class BufferedReaderWriterDemo {
    public static void main(String[] args) {
        try {
            InputStreamReader in = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(in);
            FileWriter fw = new FileWriter("a.txt");
            BufferedWriter bf = new BufferedWriter(fw);
            String input = null;
            while (!(input = br.readLine()).equals("exit")) {
                bf.write(input);
                bf.newLine();
            }
            br.close();
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

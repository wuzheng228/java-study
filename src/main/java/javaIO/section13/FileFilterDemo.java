package javaIO.section13;

import java.io.File;

public class FileFilterDemo {
    public static void main(String[] args) {
        File dir = new File("demo");
        File[] files = dir.listFiles((file) -> {
            return file.isFile() && file.getName().endsWith(".text");
        });
        for (File file : files) {
            System.out.println(file.getName());
        }
    }
}

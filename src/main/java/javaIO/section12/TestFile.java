package javaIO.section12;

import java.io.File;
import java.util.Arrays;

public class TestFile {
    public static void main(String[] args) throws Exception{
        File file = new File(".");
        System.out.println("文件名" + file.getName());
        System.out.println("文件标准路径" + file.getCanonicalPath());
        // 当前文件夹下创建目录demo
        File dir = new File(file, "demo");
        if (!dir.exists()) {
            dir.mkdir();
        }
        // 在dir目录中创建test.text
        File test = new File(dir, "test.text");
        if (!test.exists()) {
            test.createNewFile();
        }
        // 获取dir目录中的所有文件
        File[] files = dir.listFiles();
        // 显示dir目录中所有文件的名字
        System.out.println(Arrays.toString(files));
        // 显示test文件的绝对路径
        System.out.println(test.getCanonicalPath());
    }
}

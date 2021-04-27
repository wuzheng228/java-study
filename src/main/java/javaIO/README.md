# 1 File类
File类是java.io包下作为文件和目录的类。
支持操作：新建、删除、重命名
注意：对于文件的读写需要使用输入输出流，而不是File类
## 1.1 文件的基本概念
**文件:**

- 标准文件
  图片、音乐..
- 目录文件
  文件夹
- 虚拟内存文件
  系统运行时产生的临时文件

**文件的路径:**
- 相对路径
    相对于某一个文件的路径
- 绝对路径
    从磁盘根目录算起的路径名
  
## 1.2 File类中常用的方法
![](https://github.com/wuzheng228/java-study/blob/master/images/File%E7%B1%BB%E5%B8%B8%E7%94%A8%E6%96%B9%E6%B3%95.png?raw=true)

例子: 参见 section12
```java
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
```
测试:

![](https://github.com/wuzheng228/java-study/blob/master/images/TestFile.png?raw=true)

## 1.3 文件过滤器
File类的listFile()方法可以接收一个FileFilter参数。通过该参数可以列举指定目录下符合条件的文件
例子：参见 section13

列举出demo目录中以.text结尾的文件
```java
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
```

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
# 2 RandomAccessFile 类
RandomAccessFile

- 可以**读写**文件
- 支持随机访问，可以直接跳转至文件任意地方读写数据
- 可以访问文件部分内容
- 自由定义记录指针，可以在文件后增加内容

通过以下方法操作文件记录指针：
```java
getFilePointer() // 获取指针位置
getFilePointer() // 定位文件指针到指定位置
```
通过如下方法读写文件：
```java
int read()                              // 读取单个字节，返回单个字节数据
int read(byte b[])                      // 读取b.length个字节，返回实际的读取的字节数
int read(byte b[], int off, int len)    // 读取b.length个字节，返回实际的读取的字节数存储从b[off]开始
void write(int b)                       // 写入一个字节
void write(byte b[])                    // 将字节数组b中的字节写入文件
write(byte b[], int off, int len)       // 将字节数字从off开始的字节写入文件
```
例子：参加section20
```java
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
```
# 3 IO流的分类
java把不同的输入和输出源抽象成“流”。通过“流”可以用相同的方式访问不同的输入和输出源。
## 3.1 流的分类

1. 输入流和输出流
- 输入流
    从文件读取数据
- 输出流
    向文件中写入数据
基类(都是抽象类):
 - 输入流
    InputStream、Reader 
 - 输出流
    OutputStream、Writer 
2. 字节流字符流
区别：数据单元不同，字节流是8位的字节，字符流是16位的字符

- 字节流
    单位字节，读取二进制数据，图像声音
- 字符流
    单位字符，读取文本

3. 节点流和包装流(处理流)
区分：流是否直接与特定的地方相连

- 节点流
    可以向一个特定的IO设备读写数据的流。也叫做低级流
- 包装流
    对一个已经存在的流进行连接或封装，然后实现数据读写功能。也叫高级流。
    
    好处是只要使用相同的处理流，程序就能使用完全相同的输入输出代码来访问不同的数据源，处理流包装节点流的变化，程序实际访问的数据源也会发生变化

![](https://github.com/wuzheng228/java-study/blob/master/images/%E8%8A%82%E7%82%B9%E6%B5%81%E4%B8%8E%E5%A4%84%E7%90%86%E6%B5%81.png?raw=true)

# 4 字节流
## 4.1 InputStream / OutputStream
1. InputStream类
    抽象基类
    
    常用方法:
    - abstract int read()
    - int read(byte b[])
    - int read(byte b[], int off, int len)
    


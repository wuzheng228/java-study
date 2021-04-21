package reflectionAndAnno.section23;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ClassLoaderDemo extends ClassLoader{

    // 读取源文件转化为字节数组
    private byte[] getByte(String filename) {
        File file = new File(filename);
        int length = (int)file.length();
        byte[] contents = new byte[length];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int read = fis.read(contents);
        } catch(Exception e){
            e.printStackTrace();
        }
        return contents;
    }

    // 编译文件
    public boolean compile(String javaFile) {
        System.out.println("正在编译");
        int ret = 0;
        try {
            // 调用系统命令编译文件
            Process process = Runtime.getRuntime().exec("javac" + javaFile);
            process.waitFor();
            ret = process.exitValue();
        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return ret == 0;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> clazz = null;
        // 全限定类名的 . 替换为 /
        String fileStub = name.replace(".", "/");
        // java 源文件名
        String javaFileName = fileStub + ".java";
        // 编译后的文件名
        String classFileName = fileStub + ".class";
        File javaFile = new File(javaFileName);
        File classFile = new File(classFileName);
        // 如果指定的java源文件存在，class文件不存在
        // 或者java文件比class文件新则需要重新编译
        if (javaFile.exists() && !classFile.exists() || javaFile.lastModified() > classFile.lastModified()) {
            if (!compile(javaFileName ) || !classFile.exists()) {
                throw new ClassNotFoundException("ClassNotFoundExcption:" + javaFileName);
            }
        }
        // 如果class文件存在，则加载该文件
        if (classFile.exists()) {
            byte[] raw = getByte(classFileName);
            int divindex = name.indexOf("\\");
            String javafilename = null;
            if (divindex != -1) {
                javafilename = name.substring(divindex + 1);
            }
            clazz = defineClass(javafilename, raw,0, raw.length);
        }
        if (clazz == null) {
            throw new ClassNotFoundException(name);
        }
        return clazz;
    }

    public static void main(String[] args) throws Exception{
        String proClass = "reflectinAndAnno.section23.Demo";
        ClassLoaderDemo cld = new ClassLoaderDemo();
        // 加载需要运行的类
        Class<?> aClass = cld.loadClass(proClass);
    }
}

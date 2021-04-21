package reflectionAndAnno.section15;

public class StaticDemo {
    public static final String a = "ZZspace";
    static {
        System.out.println("静态代码块：正在初始化");
    }
}

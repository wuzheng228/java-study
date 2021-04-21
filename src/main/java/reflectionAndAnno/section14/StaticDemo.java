package reflectionAndAnno.section14;

public class StaticDemo {
    // 声明静态变量X
    public static int X = 1;

    public static void main(String[] args) {
        System.out.println(Y);
    }

    // 静态变量Y
    public static int Y = X * 2;

    // 静态代码块对x重新赋值
    static {
        X = 2;
    }
}

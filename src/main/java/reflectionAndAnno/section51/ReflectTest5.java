package reflectionAndAnno.section51;

public class ReflectTest5 {
    public static void main(String[] args) {
        try {
            Class<?> clazz = Class.forName("reflectionAndAnno.section51.Foo");// 会初始化类
//            ClassLoader.getSystemClassLoader().loadClass("reflectinAndAnno.section51.Foo"); // 不会初始化该类
            Object o = clazz.newInstance();
            System.out.println(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class Foo {
    static {
        System.out.println("静态代码块");
    }
    int a = 3;
    public double add(int b, double d) {
        return a + b + d;
    }
}

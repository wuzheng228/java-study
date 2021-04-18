package reflectinAndAnno.section53;

import java.lang.reflect.Method;

public class ReflectTest7 {
    public static void main(String[] args) {
        try {
            Class<?> clazz = Class.forName("reflectinAndAnno.section53.Foo");
            Method add = clazz.getDeclaredMethod("add", new Class[]{int.class, double.class});
            Object foo = clazz.newInstance();
            // 执行方法
            Object value = add.invoke(foo, new Object[]{2, 3.5});
            System.out.println(value);
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
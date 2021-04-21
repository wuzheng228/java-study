package reflectionAndAnno.section52;

import java.lang.reflect.Field;

public class ReflectTest6 {
    public static void main(String[] args) {
        try {
            Class<?> c = Class.forName("reflectionAndAnno.section52.Foo");
            Field field = c.getDeclaredField("a");
            Object foo = c.newInstance();
            Object value = field.get(foo);
            System.out.println(value);
            field.set(foo, 5);
            System.out.println(field.get(foo));
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

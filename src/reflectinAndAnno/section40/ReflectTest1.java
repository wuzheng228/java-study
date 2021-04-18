package reflectinAndAnno.section40;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Date;

public class ReflectTest1 {
    public static void main(String[] args) {
        Date date = new Date();
        // 获取类对象
        Class c = date.getClass();
        // 获取类对象中的信息,类名
        System.out.println(c.getName());
        // 获取Date类中所有构造器的实例数组
        Constructor[] constructors = c.getConstructors();
        // 输出每个构造器的信息
        for (Constructor constructor : constructors) {
            // 获得修饰符，通过Modifier.toString()转化为字符串
            int modifiers = constructor.getModifiers();
            System.out.print(Modifier.toString(modifiers) + " ");
            System.out.print(constructor.getName() + "(");
            // 获取参数类型的类对象
            Class[] parameterTypes = constructor.getParameterTypes();
            for (Class paramType : parameterTypes) {
                System.out.print(paramType.getName() + " ");
            }
            System.out.println(")");
        }
    }
}

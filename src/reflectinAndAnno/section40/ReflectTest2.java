package reflectinAndAnno.section40;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Date;

public class ReflectTest2 {
    public static void main(String[] args) {
        Date date = new Date();
        // 获取类对象
        Class c = date.getClass();
        System.out.println(c.getName());
        // 获取date class对象的所有构造器实例数组
        Constructor[] constructors = c.getConstructors();
        for (Constructor constructor : constructors) {
            int modifiers = constructor.getModifiers();
            System.out.print(Modifier.toString(modifiers) + " ");
            System.out.print(constructor.getName() + "(");
            Class[] parameterTypes = constructor.getParameterTypes();
            for (Class paramType : parameterTypes) {
                System.out.print(paramType.getName() + " ");
            }
            System.out.println(")");
        }
    }
}

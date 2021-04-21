package reflectionAndAnno.section40;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Date;

public class ReflectTest4 {
    public static void main(String[] args) {
        Date date = new Date();
        Date date1 = new Date();

        // 获取类对象
        Class c = date.getClass();
        System.out.println(c == date1.getClass());
        System.out.println(c.getName());
        // 获取Date类中所有的方法
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            String modifier = Modifier.toString(method.getModifiers());
            String returnType = method.getReturnType().getName();
            String methodName = method.getName();
            Class<?>[] parameterTypes = method.getParameterTypes();
            System.out.print(modifier + " " + returnType + " " + methodName + "(");
            for (Class paramType : parameterTypes) {
                System.out.print(paramType.getName() + " ");
            }
            Class<?>[] exceptionTypes = method.getExceptionTypes();
            System.out.println(")throws" + Arrays.asList(exceptionTypes));
        }
    }
}

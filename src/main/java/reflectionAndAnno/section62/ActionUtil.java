package reflectionAndAnno.section62;


import java.lang.reflect.Method;

public class ActionUtil {
    public static void invoke(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            Object obj = clazz.newInstance();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                System.out.println(method.getName());
                // 注意注解一定要保留到运行期
                if (method.getAnnotation(MyAnnotationWithField.class) != null) {
                    method.invoke(obj, new Object[] {});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ActionUtil.invoke("reflectinAndAnno.section62.Test");
    }
}

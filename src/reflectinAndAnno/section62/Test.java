package reflectinAndAnno.section62;

import java.lang.reflect.Method;

public class Test {
    @MyAnnotation
    public void test() {
        System.out.println("test: MyAnnotation");
    }

    @MyAnnotationWithField(id = 1, description = "消灭猕猴桃")
    public void  test2() {
        System.out.println("test2正在执行..");
    }

    public static void main(String[] args) {
        try {
            Class<?> clazz = Class.forName("reflectinAndAnno.section62.Test");
            Object obj = clazz.newInstance();
            Method test2 = clazz.getMethod("test2", new Class[]{});
            MyAnnotationWithField annotation = test2.getAnnotation(MyAnnotationWithField.class);
            if (annotation != null) {
                System.out.println(annotation.id() + " " + annotation.description());
                test2.invoke(obj, new Object[]{});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

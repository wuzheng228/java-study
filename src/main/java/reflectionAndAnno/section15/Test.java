package reflectionAndAnno.section15;

public class Test {
    public static void main(String[] args) {
        System.out.println(StaticDemo.a);
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        try {
            loader.loadClass("reflectionAndAnno.section15.StaticDemo");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

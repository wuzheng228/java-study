package reflectionAndAnno.section61;

import java.util.ArrayList;
import java.util.List;


public class AnnotationDemo {
    @Deprecated
    public void demo() {

    }

    @SuppressWarnings(value = "unchecked")
    public static void main(String[] args) {
        AnnotationDemo demo = new AnnotationDemo();
        demo.demo();
        List list = new ArrayList();
    }
}

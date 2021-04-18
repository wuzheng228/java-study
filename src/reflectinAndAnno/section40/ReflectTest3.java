package reflectinAndAnno.section40;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

public class ReflectTest3 {
    public static void main(String[] args) {
        Date date = new Date();
        // 获取类对象
        Class c = date.getClass();
        System.out.println(c.getName());
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            String modifiers = Modifier.toString(field.getModifiers());
            String type = field.getType().getName();
            String name = field.getName();
            System.out.println(modifiers + " " + type + " " + name);
        }
    }
}

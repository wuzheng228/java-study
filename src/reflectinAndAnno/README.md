# 1 类的加载过程：加载、连接、初始化
## 1.1 JVM和类的关系
每次启动加一个java程序，就会对应启动一个java虚拟机进程。该Java程序的线程共享该进程的内存。进程结束时内存状态将丢失。
例子:参见section11
创建一个A类:
```java
public class A {
    public static int a = 1;
}
```
TestA类:
```java
public class TestA {
    public static void main(String[] args) {
        A.a++;
        System.out.println(A.a);
    }
}
```
输出:
2
TestB类:
```java
public class TestB {
    public static void main(String[] args) {
        System.out.println(A.a);
    }
}
```
输出:
1
这是由于TestA，与TestB是分开运行的(各一个进程)

## 1.2 类的加载 ----> class对象
将Java的字节码转化为JVM中的java.lang.Class类的对象
类加载器分为：
- 启动类加载器:
  原生代码编写
- 用户自定义类加载器
  继承自java.lang.ClassLoader
  
## 1.3 类的连接
类的连接负责把类的二进制数据合并到JRE
分为三个过程:
- 验证
    确保加载类的正确性
- 准备
    为类的静态变量分配内存，并初始化默认值
- 解析
    将类的符号引用转化为直接引用
  java类在编译阶段所引用的其他类使用符号表示的，并不是真正的内存地址，解析的过程就是将符号表示的引用转化为所引用类的真正内存地址
  
## 1.4 类的初始化
干了啥？
    执行静态代码块，并初始化静态域
需要注意的是，一个类被初始化之前他的父类也会被初始化。初始化的时候会按源码从上到下依次执行静态代码块和初始化静态域
例子：参见section14
```java
public class StaticDemo {
    // 声明静态变量X
    public static int X = 1;

    public static void main(String[] args) {
        System.out.println(Y);
    }
    // 静态代码块对x重新赋值
    static {
        X = 2;
    }
    // 静态变量Y
    public static int Y = X * 2;
}
```
输出:
4
交换代码顺序再次执行
```java
public class StaticDemo {
    // 声明静态变量X
    public static int X = 1;

    public static void main(String[] args) {
        System.out.println(Y);
    }

    // 静态变量Y
    public static int Y = X * 2;

    // 静态代码块对x重新赋值
    static {
        X = 2;
    }
}
```
输出2
## 1.5 类的初始化时机
Java通过以下6种方式初始化类:
- (1) 创建类的实例---> new
- (2) 访问某个类或接口的静态变量，或对静态变量赋值
- (3) 调用类的静态方法
- (4) 反射 (Class.forname("全限定类名"))
- (5) 初始化一个类的子类--->先初始化父类
- (6) JVM启动时标明的启动类，就是类名和文件名相同的那个类
注意: 访问**常量**(static final)不会导致类的初始化; 使用Class.loader()方法加载类时也不会对类初始化
  
例子:参见section15
```java
public class StaticDemo {
    public static final String a = "ZZspace";
    static {
        System.out.println("静态代码块：正在初始化");
    }
}
```
测试:
```java
public class Test {
    public static void main(String[] args) {
        System.out.println(StaticDemo.a);
    }
}
```
结果: 不会执行静态代码块
下面测试class.loader()
```java
public class Test {
    public static void main(String[] args) {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        try {
            loader.loadClass("reflectinAndAnno.section15.StaticDemo");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
```
结果: 也不会执行静态代码块

# 2 类加载器
## 2.1 类加载器概述
作用：将java编译器编译后的字节码文件转化为java.lang.Class文件
一个JVM启动时会形成四个类加载器组成类加载结构：

- BootStrap ClassLoader     
- Extention ClassLoader         
- App ClassLoader            
- Custom ClassLoader            

1.从下至上检测类是否已经被加载. 
   
2.从上至下来尝试加载类

![类加载结构示意图](https://github.com/wuzheng228/java-study/blob/master/images/QQ%E5%9B%BE%E7%89%8720210418085919.png?raw=true)

## 2.2 类的加载机制

- 全盘负责
    一个类加载器加载某个类时，该类引用的其他类也由该类加载器加载
- 双亲委派
    加载某个类时先尝试使用该类的父类加载器加载，无法加载时再尝试从自己的类路径中加载该类
- 缓存机制
    所有已经被加载的类都会放入缓存，当需要再次使用时先从缓存中取，没有再去读取二进制数据该类并存入缓存
  
## 2.3 自定义类加载器

1. 继承ClassLoader类
2. 重写loadClass()方法 和 findClass()方法

为了防止改变类的加载策略建议只重写 findClass()方法
例子:参见section23

# 3 反射
## 3.1 反射概述
Java的反射机制是指在运行状态获取任意类或对象的信息。 对于任意类获取其属性和方法，对于任意对象，可以调用任意方法和属性。
Java程序可以加载一个运行时才得知名称的class，获得完整构造，生成对象实体，设置fields值，唤起methods。

对于"类(class)"，有方法、属性、类,对应的有特殊类:Method类、Field类、Constructor类、Class类

## 3.2 获得Class
Class类的实例表示正在运行Java程序的类和接口。通过Class实例可以获取某个类的属性、构造器、方法，并可以修改、创建、调用。
获得Class的方式：

- 基本类型
    
    int.class;

    long.class;
- 引用类型
    
    String.class;
    
    Class.forName("java.lang.String");

    "abc".getClass();
# 4 反射获取对象信息
Class类的实例提供了一系列方法操作某个类的属性、方法、构造器

获取类信息

例子: 参见section40.ReflectTest1

获取构造器

例子: 参见section40.ReflectTest2

获取类属性

例子: 参见section40.ReflectTest3

获取类方法

例子: 参见section40.ReflectTest4
# 5 反射创建对象和访问对象
## 5.1 通过反射创建对象

1. 通过Class对象的newInstance方法，局限是只能调用默认的构造方法
2. 先通过Class对象获取Constructor对象，然后再调用Constructor类的newInstance方法

例子：参见section51.ReflectTest5
通过Class.forName创建对象：
```java
public class ReflectTest5 {
    public static void main(String[] args) {
        try {
            Class<?> clazz = Class.forName("reflectinAndAnno.section51.Foo");// 会初始化类
            Object o = clazz.newInstance();
            System.out.println(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
注意点：

1. Class.forName方法利用类名在CLASSPATH中查找对应的类，并装载到内存
2. Class.forName采用”懒惰方式“加载类，如果内存中已存在该类的类对象就直接返回
3. newInstance方法利用无参构造器创建类实例

## 5.2 通过反射访问对象的属性
例子：参见section52
```java
public class ReflectTest6 {
    public static void main(String[] args) {
        try {
            Class<?> c = Class.forName("reflectinAndAnno.section52.Foo");
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
```
注: 可以使用field.set(obj, value)来对obj对象的属性设置值
## 5.3 通过反射访问对象的方法
例子：参见section53
利用反射调用foo对象的add方法
```java
public class ReflectTest7 {
    public static void main(String[] args) {
        try {
            Class<?> clazz = Class.forName("reflectinAndAnno.section53.Foo");
            Method add = clazz.getMethod("add", new Class[]{int.class, double.class});
            Object foo = clazz.newInstance();
            // 执行方法
            Object value = add.invoke(foo, new Object[]{2, 3.5});
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
获取了某个类的Class对象后可以通过getMethod方法来获取Method对象，得到Method对象后调用invoke方法来执行，传参为Foo对象，和方法参数
# 6 注解
## 6.1 基本注解
注解就是一种标记，可以用在包、类、字段、方法、方法参数以及局部变量上。注解本身没有什么作用，但是可以通过反射来了解类和各元素上有没有标记，根据标记来做不同的事情。

语法: @注解名

根据注解参数的个数可以分为：标记注解、单值注解、完全注解。

内置的三个基本注解:

- @Override 表示当前方法重写父类的方法

- @Desprecated 表示当前方法已经过时

- @SuppressWarning 表示关闭不当的编译器信息

例子1:参见section61
父类：
```java
public class Person {
    public void test() {
        
    }
}
```
子类：
```java
public class Student extends Person{
    @Override
    public void test() {
        super.test();
    }
}
```
student类重写的父类的方法，如果父类中无该方法则@Override就会报错
例子2：参见section61
```java
public class AnnotationDemo {
    @Deprecated
    public static void demo() {
        
    }

    public static void main(String[] args) {
        demo();
    }
}
```
加了@Deprecated注解的方法在被调用是会多一条横线

@SuppressWaring可以用的参数有

- deprecation 使用了过时的类或方法的警告

- unchecked 执行了未检查的转换时的警告

- fallthrough swith语句一直往下通没有break的警告
  
- path 在类路径、源文件路径等中有不存在路径的警告
  
- serial 当在可序列化的类上缺少serialVersionUID的警告

- finally finally语句不能正常完成的警告

- all 关闭以上所有情况的警告

例子：参见section61
```java
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
```

## 6.2 自定义注解
语法：类似接口类的定义但使用的是`@interface`
例子:参考section62
```java
public @interface MyAnnotation {
}
```
可以使用该自定义注解修饰方法、类等
```java
public class Test {
    @MyAnnotation
    public void test() {
        
    }
}
```
在自定义注解时也可以自定义带**成员变量**的注解,定义方法类似无参方法
```java
public @interface MyAnnotationWithField {
    int id();
    String description();
}
```
测试:
```java
public class Test {
    @MyAnnotationWithField(id = 1, description = "消灭猕猴桃")
    public void  test2() {

    }
}
```
也可以为注解的成员变量提供默认值
```java
public @interface MyAnnotationWithField {
    int id() default 1;
    String description() default "单元测试";
}
```
还可以指定自定义注解可以注解的位置以及是否在运行保留
```java
@Target(ElementType.METHOD) // 可用于方法
@Retention(RetentionPolicy.RUNTIME) // 在运行期保留
public @interface MyAnnotationWithField {
    int id() default 1;
    String description() default "单元测试";
}
```
使用自定义注解如果不使用反射机制是没啥用的，下面通过反射机制来获取Test类中注解的元数据：
```java
public class Test {
    @MyAnnotation
    public void test() {

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
```
为了对加了注解的方法添加影响可以写一个注解处理的工具类
```java
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
```

# 7 案例
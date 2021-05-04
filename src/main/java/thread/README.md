# 线程
## 线程概述
进程是相互隔离独立运行的程序。线程是进程的细分，共享进程的资源，线程之间的切换消耗小，但不利于管理。
## Java中的线程
线程运行的状态图:

<img src="https://github.com/wuzheng228/java-study/blob/master/images/%E7%BA%BF%E7%A8%8B%E8%BF%90%E8%A1%8C%E7%8A%B6%E6%80%81%E5%9B%BE.jpg?raw=true" width="70%"/>

## 使用线程的原因
执行长时间任务，比如安卓所有的UI操作在UI线程，长时间操作因放在其他线程，防止UI界面卡死
## 创建多线程的方式

- 继承Thread类重写run方法
- 实现Runnable接口
## 线程的生命周期
1.创建和就绪

new一个线程之后处于创建状态，调用start方法后处于就绪状态，但不会开始执行，只是代表该线程可以运行了，对于何时运行取决于取决于JVM中的线程调度器调度
   
2.运行和阻塞状态
    
处于就绪状态的线程获得CPU就会开始执行线程的run()方法，则线程处于运行状态。

    
线程并不会一直运行，当CPU分配的时间片用完之后，系统就会剥夺该线程所占用的资源，给其他线程执行机会，具体执行哪个线程需要考虑线程优先级

以下情况会使线程进入阻塞状态:
1. 线程调用了sleep方法
2. 线程调用了一个阻塞IO的方法，在该方法返回之前线程被阻塞
3. 线程在等待某个通知
4. 程序调用了线程的suspend方法，将该线程挂起

3.线程死亡
1. run方法执行完成，线程正常结束
2. 线程出现Exception或Error
3. 直接调用了线程的stop()方法

判断线程是否死亡可以使用isAlive方法，如果线程处于新建、死亡状态时返回true

## 线程操作

1.join

当主线程需要等待子线程返回结果时就需要使用join

例子:
```java
public class JoinThreadDemo extends Thread{

    public JoinThreadDemo(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(getName() + ":" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            if (i == 20) {
                JoinThreadDemo demo = new JoinThreadDemo("被join线程");
                try {
                    demo.start();
                    demo.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() +":" + i);
        }
    }
}
```
没加join时，往往是主线程先执行完毕，加入join之后主线程执行到19后会等待子线程执行完毕再继续执行

2.后台线程（守护线程）

后台线程会随着主程序的结束而结束，而前台线程不会。只要有一个前台线程没有退出，进程就不会终止。

默认情况下，程序员创建的是用户线程（前台线程）； 使用setDaemon(true)设置线程为后台线程； 使用isDaemon可以判断一个线程是后台线程还是前台线程。

JVM的垃圾回收器就是一个后台线程

setDaemon函数必须在start方法之前设定，否则就会出现IllegalThreadStateException

```java
public class DaemonThread extends Thread {
    public static void main(String[] args) {
        DaemonThread t = new DaemonThread();
        t.setDaemon(true);
        t.start();
        for (int i = 0; i < 10; i ++) {
            System.out.println(Thread.currentThread().getName() + "=======" + i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("end main");
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println(getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
t设置成后台线程后会随着主线程的结束而结束，否则就会一直运行。

3.线程睡眠

让正在执行的线程暂停一段时间，并进入阻塞状态（不会释放锁）。调用Thread.sleep方法实现

- sleep(long millis) 让当前线程暂停millis毫秒

- sleep(long millis, int nanos) 让当前线程暂停millis毫秒+nanos微妙

精度受系统计时器和线程调度器的精度和准确度影响

4.线程让步
让线程回到可运行状态，运行具有相同优先级的其他线程获得运行机会。

yield的目的就是让相同优先级的线程之间能够适当的轮转运行，但是实际中无法保证能达到让步的目的，因为让步的线程有可能被线程调度器再次选中。

```java
public class YieldThread extends Thread{
    public YieldThread(String name) {
        super(name);
    }
    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println(getName() +":" + i);
            if (i == 20) {
                Thread.yield();
            }
        }
    }
    public static void main(String[] args) {
        YieldThread t1 = new YieldThread("高级");
        t1.start();
        YieldThread t2 = new YieldThread("低级");
        t2.start();
    }
}
```
5.线程优先级
线程的优先级范围在1-10之间。JVM线程调度程序是基于优先级的抢先调度机制。

注意：设计多线程程序不要依赖线程的优先级。因为线程调度优先级操作没有保障，线程优先级只能当作一种提高程序效率的方法

当线程池中的线程优先级相同时，调度程序可能会采用以下两种操作

- 选择一个喜欢的线程运行直到阻塞或运行完成
- 时间分片，为每个线程提供均等的运行机会

线程的默认优先级是创建它的执行线程的优先级，通过setPriority设置优先级

1-10个优先级有些JVM可能不认，会进行合并。

Thread类有三个常量定义线程的优先级范围

- MAX_PRIORITY
- MIN_PRIORITY
- NORM_PRIORITY

```java
public class PriorityThread extends Thread {
    public PriorityThread(String name) {
        super(name);
    }

    public void run () {
        for (int i = 0; i < 50; i++) {
            System.out.println(getName() + ":" + i);
        }
    }

    public static void main(String[] args) {
        PriorityThread t1 = new PriorityThread("高优先级");
        t1.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        PriorityThread t2 = new PriorityThread("低优先级");
        t2.setPriority(Thread.MIN_PRIORITY);
        t2.start();
    }
}
```
高优先级的线程有更多机会先执行完

## 线程同步
### 线程安全问题
多个线程访问共享资源时会引起冲突，需要引入“同步”机制，让各线程之间有顺序的访问。

只有变量需要同步，常量不需要同步

### 线程并发演示

一张卡里有2000元，夫妻俩同时取1500，如果没有同步则可以取出3000元

```java
public class ThreadTest {
    public double money = 2000;

    public static void main(String[] args) {
        ThreadTest test = new ThreadTest();
        test.quqian();
    }
    
    public void quqian() {
        MyThread myThread1 = new MyThread("丈夫", 1500);
        MyThread myThread2 = new MyThread("妻子", 1500);
        myThread1.start();
        myThread2.start();
    }

    class MyThread extends Thread {
        private double money1;
        String name;

        public MyThread(String name, double money1) {
            this.money1 = money1;
            this.name = name;
        }

        @Override
        public void run() {
            if (money > money1) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                money -= money1;
                System.out.println(name + "取钱成功");
            }
        }
    }
}
```
上面的代码出现了线程并发问题，两人取钱都成功了

### 线程同步方法

可以给共享资源加锁来解决线程同步问题。

访问同一个资源的不同代码片段应该加上同一个同步锁

```java
public class ThreadTest {

    private static final Object lock = new Object();

    public double money = 2000;

    public static void main(String[] args) {
        ThreadTest test = new ThreadTest();
        test.quqian();
    }

    public void quqian() {
        MyThread myThread1 = new MyThread("丈夫", 1500);
        MyThread myThread2 = new MyThread("妻子", 1500);
        myThread1.start();
        myThread2.start();
    }

    class MyThread extends Thread {
        private double money1;
        String name;

        public MyThread(String name, double money1) {
            this.money1 = money1;
            this.name = name;
        }

        @Override
        public void run() {
            synchronized (lock) {
                if (money > money1) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    money -= money1;
                    System.out.println(name + "取钱成功");
                } else {
                    System.out.println(name + "取钱失败余额不足");
                }
            }
        }
    }
}
```
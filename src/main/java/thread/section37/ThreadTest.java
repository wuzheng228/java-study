package thread.section37;

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

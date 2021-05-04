package thread.section36;

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

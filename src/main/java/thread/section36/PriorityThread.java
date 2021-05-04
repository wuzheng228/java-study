package thread.section36;

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

package thread.section36;

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

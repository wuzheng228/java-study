package thread.section36;

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
//                try {
                    demo.start();
//                    demo.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
            System.out.println(Thread.currentThread().getName() +":" + i);
        }
    }
}

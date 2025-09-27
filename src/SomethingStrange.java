
public class SomethingStrange {
    private static final Object lock = new Object();
    private static boolean isOneTurn = true;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while(true) {
                synchronized(lock) {
                    while (!isOneTurn) {
                        try { lock.wait(); } catch (InterruptedException e) {}
                    }
                    System.out.print("1");
                    isOneTurn = false;
                    lock.notify();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            while(true) {
                synchronized(lock) {
                    while (isOneTurn) {
                        try { lock.wait(); } catch (InterruptedException e) {}
                    }
                    System.out.print("2");
                    isOneTurn = true;
                    lock.notify();
                }
            }
        });

        t1.start();
        t2.start();
    }
}

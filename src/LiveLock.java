public class LiveLock {
    private static final Object lock = new Object();
    private static boolean turn = true;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                synchronized(lock) {
                    if (!turn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {}
                    }
                    System.out.println("1");
                    turn = false;
                    lock.notifyAll();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                synchronized(lock) {
                    if (turn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {}
                    }
                    System.out.println("2");
                    turn = true;
                    lock.notifyAll();
                }
            }
        });

        t1.start();
        t2.start();
    }
}

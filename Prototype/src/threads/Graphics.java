package threads;

/**
 * Created by FakeYou on 3/25/14.
 */
public class Graphics extends Thread {
    public void run() {
        while(true) {
            try {
//                System.out.println("graphics");
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

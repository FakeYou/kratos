package threads;

/**
 * Created by FakeYou on 3/25/14.
 */
public class Communication extends Thread {
    public void run() {
        while(true) {
            try {
//                System.out.println("communication");
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

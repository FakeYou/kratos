package threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by FakeYou on 3/25/14.
 */
public class Input extends Thread {
    public void run() {
        while(true) {
            try {
//                System.out.println("input");

                BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                String buffer = bufferRead.readLine();

                if(buffer != null) {
                    System.out.println(buffer);
                }

                sleep(1);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package threads;

/**
 * Created by FakeYou on 3/25/14.
 */
public class Main {
    public static void main(String[] args) {
        Communication communication = new Communication();
        Graphics graphics = new Graphics();
        Input input = new Input();

        communication.start();
        graphics.start();
        input.start();
    }
}

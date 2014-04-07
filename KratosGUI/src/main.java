import javax.swing.*;
import java.awt.*;

/**
 * Created by Yuri on 2/4/2014.
 */
public class main {
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex){

        }

        GameView view = new GameView(GameView.REVERSI, "Test1", "Test2", 1);
        view.setPreferredSize(new Dimension(500, 500));
        view.pack();
        view.setVisible(true);
    }
}

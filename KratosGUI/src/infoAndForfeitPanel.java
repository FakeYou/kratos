import javax.swing.*;
import java.awt.*;

/**
 * Created by Yuri on 2/4/2014.
 */
public class infoAndForfeitPanel extends JPanel {
    String text;

    public infoAndForfeitPanel(String gameName, String playerName, int width){
        setText(gameName, playerName);
        this.setPreferredSize(new Dimension(width, 60));

        // Add label for player and game information
        this.add(new Label(text));

        // Add forfeit button
        JButton forfeitButton = new JButton("Forfeit");
        // TODO: Add action listener to forfeit game. btn.addActionListener(...);
        this.add(forfeitButton);
        this.setVisible(true);
    }

    public void setText(String gameName, String playerName){
        this.text = "Playing " + gameName + " versus: " + playerName + ".";
    }
}

package app;

import framework.Kratos;
import framework.communication.CommandListener;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by FakeYou on 3/29/14.
 */
public class App {
    private final Kratos kratos;

    public App() {
        JFrame frame = new JFrame("application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        final JTextPane username = new JTextPane();
        frame.getContentPane().add(username);

        final JLabel label = new JLabel("Login");
        frame.getContentPane().add(label);

        JButton button = new JButton("login");
        frame.getContentPane().add(button);

        JButton playerlistButton = new JButton("playerlist");
        frame.getContentPane().add(playerlistButton);

        final DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        model.addColumn("username");
        model.addColumn("option");
        frame.getContentPane().add(table);

        kratos = new Kratos();

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommandListener listener = new CommandListener() {
                    @Override
                    public void trigger(Boolean success, String response) {
                        label.setText(response);
                    }
                };

                kratos.getInterpreter().login(username.getText(), listener);
            }
        });

        playerlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommandListener listener = new CommandListener() {
                    @Override
                    public void trigger(Boolean success, String response) {
                        String list = response.replace("SVR PLAYERLIST [", "").replace("]", "");

                        String[] usernames = list.split(",");

                        model.setNumRows(0);

                        for(String username : usernames) {
                            username = username.replace("\"", "").trim();
                            model.addRow(new Object[] {username, new JButton("challenge")});
                        }
                    }
                };

                kratos.getInterpreter().get("playerlist", listener);
            }
        });

        frame.pack();
        frame.setVisible(true);

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                kratos.getCommunication().disconnect();
            }
        });
    }

    public static void main(String[] args) {
        new App();
    }
}

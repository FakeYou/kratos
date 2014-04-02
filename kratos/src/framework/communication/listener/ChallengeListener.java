package framework.communication.listener;

import framework.communication.CommandListener;
import framework.communication.CommunicationListener;

import java.util.ArrayList;

/**
 * Created by FakeYou on 3/29/14.
 */
public class ChallengeListener implements CommunicationListener {

    private Boolean listening = true;
    private ArrayList<CommandListener> listeners;

    public ChallengeListener() {
        listeners = new ArrayList<CommandListener>();
    }

    @Override
    public Boolean trigger(String message) {
        String ChallengePatten = "^(SVR GAME CHALLENGE \\{CHALLENGER: \").+(\", GAMETYPE: \")[a-zA-Z ]+(\", CHALLENGENUMBER: \")[0-9]+(\"\\})$";

        if(message.matches(ChallengePatten)) {
            System.out.println(message);
            informListeners(true, message);
            return true;
        }

        return false;
    }

    @Override
    public void setListening(Boolean listening) {
        this.listening = listening;
    }

    @Override
    public Boolean isListening() {
        return listening;
    }

    private void informListeners(Boolean success, String response) {
        for(CommandListener listener : listeners) {
            listener.trigger(success, response);
        }
    }

    @Override
    public void addListener(CommandListener listener) {

    }
}

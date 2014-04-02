package framework.communication;

/**
 * Created by FakeYou on 3/29/14.
 */
public interface CommunicationListener {
    public Boolean trigger(String message);

    public void setListening(Boolean listening);
    public Boolean isListening();

    public void addListener(CommandListener listener);
}

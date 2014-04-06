package org.kratos.framework.communication;

/**
 * Created by FakeYou on 3/29/14.
 */
public interface CommunicationListener {
    public enum resolved {
        INCOMPLETE,
        PARTIAL,
        COMPLETE,
    }

    public resolved trigger(String message);

    public void setListening(Boolean listening);
    public Boolean isListening();

    public void addListener(CommandListener listener);
}

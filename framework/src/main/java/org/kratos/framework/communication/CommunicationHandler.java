package org.kratos.framework.communication;

/**
 * Created by FakeYou on 3/29/14.
 */
public interface CommunicationHandler {
    public void connect(String host, int port);
    public void disconnect();

    public Boolean isReady();
    public void setReady(Boolean ready);

    public void write(String message);

    public void addListener(CommunicationListener listener);
}

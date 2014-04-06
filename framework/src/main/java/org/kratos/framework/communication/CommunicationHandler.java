package org.kratos.framework.communication;

import java.io.IOException;

/**
 * Created by FakeYou on 3/29/14.
 */
public interface CommunicationHandler {
    public void connect(String host, int port) throws IOException;
    public void disconnect();

    public Boolean isReady();
    public void setReady(Boolean ready);

    public Boolean isBusy();
    public void setBusy(Boolean busy);

    public void write(String message);

    public void addListener(CommunicationListener listener);
}

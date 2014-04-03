package org.kratos.framework.communication;

/**
 * Created by FakeYou on 3/29/14.
 */
public interface CommandListener {
    public void trigger(Boolean success, String response);
}

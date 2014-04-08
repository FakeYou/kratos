package org.kratos.framework.communication.listener;

import org.kratos.framework.communication.CommandListener;
import org.kratos.framework.communication.Communication;
import org.kratos.framework.communication.CommunicationListener;
import org.kratos.framework.communication.command.LoginCommand;

import java.util.ArrayList;

/**
 * Created by FakeYou on 3/29/14.
 */
public class LoginListener extends AbstractListener {

    public LoginListener(LoginCommand command) {
        super(command);
    }

    @Override
    public resolved trigger(String message) {
        String OkPattern = "^(OK)$";
        String ErrPattern = "^(ERR).+";
        String ErrDuplicatePattern = "^(ERR Duplicate name exists)$";
        String ErrLoggedInPattern = "^(ERR Already logged in)$";
        String ErrNoNameEntered = "^(ERR No name entered)$";

        if(message.matches(OkPattern)) {
            informListeners(Communication.status.OK, "logged in");
            listening = false;
            return resolved.COMPLETE;
        }
        else if(message.matches(ErrDuplicatePattern)) {
            informListeners(Communication.status.ERROR_LOGIN_DUPLICATE_NAME, "duplicate name");
            listening = false;
            return resolved.COMPLETE;
        }
        else if(message.matches(ErrLoggedInPattern)) {
            informListeners(Communication.status.ERROR_LOGIN_ALREADY_LOGGED_IN, "already logged in");
            listening = false;
            return resolved.COMPLETE;
        }
        else if(message.matches(ErrNoNameEntered)) {
            informListeners(Communication.status.ERROR_LOGIN_NO_NAME, "no name entered");
            listening = false;
            return resolved.COMPLETE;
        }
        else if(message.matches(ErrPattern)) {
            informListeners(Communication.status.ERROR, "unknown error");
            listening = false;
            return resolved.COMPLETE;
        }

        return resolved.INCOMPLETE;
    }
}

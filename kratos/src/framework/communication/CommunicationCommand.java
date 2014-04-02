package framework.communication;

/**
 * Created by FakeYou on 3/29/14.
 */
public interface CommunicationCommand {
    public void execute(String ... arguments) throws Exception;
    public void addListener(CommandListener listener);
}
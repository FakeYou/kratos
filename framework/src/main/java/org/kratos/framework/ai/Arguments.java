package org.kratos.framework.ai;

import com.github.jankroken.commandline.annotations.LongSwitch;
import com.github.jankroken.commandline.annotations.Option;
import com.github.jankroken.commandline.annotations.ShortSwitch;
import com.github.jankroken.commandline.annotations.SingleArgument;

/**
 * Created by FakeYou on 4/10/14.
 */
public class Arguments {
    private String host;
    private String username;
    private String gametype;
    private String challenger;
    private Integer port;

    @Option
    @LongSwitch("host")
    @ShortSwitch("h")
    @SingleArgument
    public void setHost(String host) {
        this.host = host;
    }

    @Option
    @LongSwitch("port")
    @ShortSwitch("p")
    @SingleArgument
    public void setPort(String port) {
        this.port = Integer.parseInt(port);
    }

    @Option
    @LongSwitch("username")
    @ShortSwitch("u")
    @SingleArgument
    public void setUsername(String username) {
        this.username = username;
    }

    @Option
    @LongSwitch("gametype")
    @ShortSwitch("g")
    @SingleArgument
    public void setGametype(String gametype) {
        this.gametype = gametype;
    }

    @Option
    @LongSwitch("challenger")
    @ShortSwitch("c")
    @SingleArgument
    public void setChallenger(String challenger) {
        this.challenger = challenger;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getGametype() {
        return gametype;
    }

    public String getChallenger() {
        return challenger;
    }
}

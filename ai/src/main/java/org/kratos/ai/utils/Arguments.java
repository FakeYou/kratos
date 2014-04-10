package org.kratos.ai.utils;

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
    private Integer port;

    @Option
    @LongSwitch("host")
    @ShortSwitch("h")
    @SingleArgument
    public void setHost(String host) {
        this.host = host;

        System.out.println("setHost: " + host);
    }

    @Option
    @LongSwitch("port")
    @ShortSwitch("p")
    @SingleArgument
    public void setPort(String port) {
        this.port = Integer.parseInt(port);

        System.out.println("setPort: " + port);
    }

    @Option
    @LongSwitch("username")
    @ShortSwitch("u")
    @SingleArgument
    public void setUsername(String username) {
        this.username = username;

        System.out.println("setUsername: " + username);
    }

    @Option
    @LongSwitch("gametype")
    @ShortSwitch("g")
    @SingleArgument
    public void setGametype(String gametype) {
        this.gametype = gametype;

        System.out.println("setGametype: " + gametype);
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
}

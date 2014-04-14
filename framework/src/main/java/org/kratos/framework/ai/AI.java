package org.kratos.framework.ai;

import org.kratos.framework.Kratos;
import org.kratos.framework.communication.Parser;
import org.kratos.framework.game.Match;

import java.util.Random;

/**
 * Created by FakeYou on 4/14/14.
 */
public class AI {
    protected Kratos kratos;
    protected Match match;
    protected Parser parser;

    private Arguments arguments;

    private String username;
    private String host;
    private Integer port;
    private String challenger;

    private String[] names = new String[] {
            "Jacob", "Mason", "Ethan", "Noah", "William", "Liam", "Jayden", "Michael", "Alexander", "Aiden", "Daniel",
            "Matthew", "Elijah", "James", "Anthony", "Benjamin", "Joshua", "Andrew", "David", "Joseph", "Logan",
            "Jackson", "Christopher", "Gabriel", "Samuel", "Ryan", "Lucas", "John", "Nathan", "Isaac", "Dylan",
            "Caleb", "Christian", "Landon", "Jonathan", "Carter", "Luke", "Owen", "Brayden", "Gavin", "Wyatt",
            "Isaiah", "Henry", "Eli", "Hunter", "Jack", "Evan", "Jordan", "Nicholas", "Tyler", "Aaron", "Jeremiah",
            "Julian", "Cameron", "Levi", "Brandon", "Angel", "Austin", "Connor", "Adrian", "Robert", "Charles",
            "Thomas", "Sebastian", "Colton", "Jaxon", "Kevin", "Zachary", "Ayden", "Dominic", "Blake", "Jose",
            "Oliver", "Justin", "Bentley", "Jason", "Chase", "Ian", "Josiah", "Parker", "Xavier", "Adam", "Cooper",
            "Nathaniel", "Grayson", "Jace", "Carson", "Nolan", "Tristan", "Luis", "Brody", "Juan", "Hudson", "Bryson",
            "Carlos", "Easton", "Damian", "Alex", "Kayden", "Ryder", "Sophi", "Emma", "Isabella", "Olivi", "Av", "Emil",
            "Abigai", "Mia", "Madison", "Elizabeth", "Chloe", "Ell", "Avery", "Addison", "Aubrey", "Lily", "Natalie",
            "Sofia", "Charlotte", "Zoey", "Grace", "Hannah", "Amelia", "Harper", "Lillian", "Samantha", "Evelyn",
            "Victoria", "Brooklyn", "Zoe", "Layla", "Haile", "Leah", "Kaylee", "Anna", "Aaliyah", "Gabriella",
            "Allison", "Nevaeh", "Alexis", "Audrey", "Savannah", "Sarah", "Alyssa", "Claire", "Taylor", "Rile",
            "Camil", "Arianna", "Ashley", "Brianna", "Sophie", "Peyto", "Bella", "Khloe", "Genesis", "Alexa",
            "Serenity", "Kylie", "Aubree", "Scarlett", "Stella", "Maya", "Katherine", "Julia", "Lucy", "Madelyn",
            "Autumn", "Makayla", "Kayla", "Mackenzie", "Lauren", "Gianna", "Ariana", "Faith", "Alexandr", "Melanie",
            "Sydney", "Bailey", "Caroline", "Naomi", "Morgan", "Kennedy", "Ellie", "Jasmine", "Eva", "Skylar",
            "Kimberly", "Violet", "Molly", "Aria", "Jocelyn", "Trinity", "Londo", "Lydia", "Madeline", "Reagan",
            "Piper", "Andrea", "Annabelle"
    };

    public AI() {
        kratos = new Kratos();
        match = kratos.getMatch();
        parser = kratos.getParser();
        arguments = new Arguments();
    }

    public void beforeStart() {
        if(getUsername() == null) {
            System.err.println("username was not set");
            System.exit(0);
        }
        else if(getHost() == null) {
            System.err.println("host was not set");
            System.exit(0);
        }
        else if(getPort() == null) {
            System.err.println("port was not set");
            System.exit(0);
        }
    }

    public void setArguments(Arguments arguments) {
        this.arguments = arguments;
    }

    public String getUsername() {
        if(username != null) {
            return username;
        }

        return arguments.getUsername();
    }

    public String getHost() {
        if(host != null) {
            return host;
        }

        return arguments.getHost();
    }

    public Integer getPort() {
        if(port != null) {
            return port;
        }

        return arguments.getPort();
    }

    public String getChallenger() {
        if(challenger != null) {
            return challenger;
        }

        return arguments.getChallenger();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String setRandomUsername() {
        String name = "Bot " + names[new Random().nextInt(names.length)];

        this.username = name;

        return name;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setChallenger(String challenger) {
        this.challenger = challenger;
    }
}

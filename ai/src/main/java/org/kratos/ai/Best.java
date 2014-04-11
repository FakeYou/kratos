package org.kratos.ai;

/**
 * Created by FakeYou on 4/11/14.
 */
public class Best {
    public int x;
    public int y;
    public int value;

    public Best(int value) {
        this(value, 0, 0);
    }

    public Best(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

}

package com.WitchHunter.GameObject.WitchHunterObject.Display;

import com.WitchHunter.Controller.SceneController;
import com.WitchHunter.GameObject.GameObject;
import com.WitchHunter.utils.Path;
import java.awt.*;
import static com.WitchHunter.utils.Global.*;

public class Letter extends GameObject {

    public enum Type{
        A(new Path().img().letters().letterA()),
        B(new Path().img().letters().letterB()),
        C(new Path().img().letters().letterC()),
        D(new Path().img().letters().letterD()),
        E(new Path().img().letters().letterE()),
        F(new Path().img().letters().letterF()),
        G(new Path().img().letters().letterG()),
        H(new Path().img().letters().letterH()),
        I(new Path().img().letters().letterI()),
        J(new Path().img().letters().letterJ()),
        K(new Path().img().letters().letterK()),
        L(new Path().img().letters().letterL()),
        M(new Path().img().letters().letterM()),
        N(new Path().img().letters().letterN()),
        O(new Path().img().letters().letterO()),
        P(new Path().img().letters().letterP()),
        Q(new Path().img().letters().letterQ()),
        R(new Path().img().letters().letterR()),
        S(new Path().img().letters().letterS()),
        T(new Path().img().letters().letterT()),
        U(new Path().img().letters().letterU()),
        V(new Path().img().letters().letterV()),
        W(new Path().img().letters().letterW()),
        X(new Path().img().letters().letterX()),
        Y(new Path().img().letters().letterY()),
        Z(new Path().img().letters().letterZ());

        private String path;

        Type(String path) {
            this.path = path;
        }
    }

    private final Image letterImg;

    public Letter(int x, int y, Type type) {
        super(x, y, UNIT_X, UNIT_Y);
        letterImg = SceneController.instance().irc().tryGetImage(type.path);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(letterImg,painter().left(), painter().top(), UNIT_X*2, UNIT_Y*2,null);
    }

    @Override
    public void update() {

    }
}

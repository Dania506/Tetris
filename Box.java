package com.company;

import javax.swing.*;

public class Box extends JPanel {
    private int color;
    public int getColor(){
        return color;
    }
    public Box(int x, int y) {
        color = 0;
        setBounds(x * allConst.SIZE, y * allConst.SIZE,
                allConst.SIZE, allConst.SIZE);
        setBackground(allConst.COLORS[0]);

    }

    public void setColor(int color) {
        this.color = color;

        if (color >= 0 && color < allConst.COLORS.length)
            setBackground(allConst.COLORS[color]);

    }

}

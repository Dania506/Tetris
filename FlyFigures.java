package com.company;

public class FlyFigures {
    private Figure figure;
    private Coord coord;
    private boolean landed;
    private int ticks;
    Mapable map;

    public FlyFigures(Mapable map) {
        this.map = map;
        figure = Figure.getRandom();
        coord = new Coord(allConst.WIDTH / 2 - 2, - 1); // чтобы был виден один пиксель
        landed = false;
        ticks = 2;
    }



    public Figure getFigure() {
        return figure;
    }

    public Coord getCoord() {
        return coord;
    }

    public  boolean Landed() { //преземлилась
        return landed;

    }
    public boolean canPlace() {
        return canMove(figure,0,0);
    }

    private boolean canMove(Figure figure, int sx, int sy) { // can we move the figure
        if (coord.x + sx + figure.top.x < 0) return false;
        if (coord.x + sx + figure.bot.x >= allConst.WIDTH) return false;
        if (coord.y + sy + figure.bot.y >= allConst.HEIGHT) return false;
        for (Coord dot : figure.dots)
            if (map.getBoxColor(coord.x + dot.x + sx, coord.y + dot.y + sy) > 0) //чтобы фигурка стояла на фигурке
                return false;
        return true;
    }

    public void moveFigure(int sx, int sy) {
        if (canMove(figure, sx, sy))
            coord = coord.plus(sx, sy);
        else {
            if (sy == 1){
                if (ticks > 0)
                    ticks--;
                else
                    landed = true;


            }
        }
    }

    public void turnFigure(int direction) {      // не может повернуться возле стенки
        Figure rotated = direction == 1 ? figure.turnRight() : figure.turnLeft();
        if (canMove(rotated, 0, 0))
            figure = rotated;

        else if (canMove(rotated, 1, 0)) {
            figure = rotated;
            moveFigure(1, 0);

        } else if (canMove(rotated, -1, 0)) {
            figure = rotated;
            moveFigure(-1, 0);
        }
    }


}

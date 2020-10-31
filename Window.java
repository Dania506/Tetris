package com.company;

import javax.swing.*;
import javax.swing.Box;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame implements Runnable, Mapable {


    private javax.swing.Box[][] boxes;
    private FlyFigures fly;




    public Window() {
        boxes = new javax.swing.Box[allConst.WIDTH][allConst.HEIGHT];
        inForm();
        inBoxes();
        addKeyListener(new KeyAdapter());
        TimeAdapter timeAdapter = new TimeAdapter();
        Timer timer = new Timer(300, timeAdapter);
        timer.start();



    }

    public void addFigure() {
        String s = "Game Over!";
        fly = new FlyFigures(this);
        if (!fly.canPlace()) {
            setVisible(false);


        }
        showFigure();
    }

    private void inForm() {   // реализация формы : form implementation
        setSize(allConst.WIDTH * allConst.SIZE + 15,
                allConst.HEIGHT * allConst.SIZE + 40);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tetris");
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(null); // paint not all window
    }

    private void inBoxes() {   //dots for figure
        for (int x = 0; x < allConst.WIDTH; x++)  //переберает все координаті х
            for (int y = 0; y < allConst.HEIGHT; y++) {   //все у
                boxes[x][y] = new Box(x, y);
                add(boxes[x][y]);  // add to panel
            }

    }

    @Override
    public void run() {
        repaint();

    }

    private void showFigure() {
        showFigure(1);
    }

    private void hideFigure() {
        showFigure(0);
    }

    private void showFigure(int color) {
        for (Coord dot : fly.getFigure().dots) //для каждой координаты в фигуре
            setBoxColor(fly.getCoord().x + dot.x, fly.getCoord().y + dot.y, color);
    }

    private void setBoxColor(int x, int y, int color) {
        if (x < 0 || x >= allConst.WIDTH) return; // проверка если выходит за рамки высоты
        if (y < 0 || y >= allConst.HEIGHT) return; // за рамки ширины
        boxes[x][y].setColor(color);
    }

    public int  getBoxColor(int x, int y){
        if (x < 0 || x >= allConst.WIDTH) return -1 ; // проверка если выходит за рамки высоты
        if (y < 0 || y >= allConst.HEIGHT) return -1 ;
        return boxes[x][y].getColor();
    }

    private void moveFly(int sx, int sy) {
        hideFigure();
        fly.moveFigure(sx, sy);
        showFigure();
    }

    private void turnFly(int direction) {
        hideFigure();
        fly.turnFigure(direction);
        showFigure();
    }


    class KeyAdapter implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    moveFly(-1, 0);
                    break; // переместили на лево
                case KeyEvent.VK_RIGHT:
                    moveFly(+1, 0);
                    break;
                case KeyEvent.VK_DOWN:
                    moveFly(0, +1);
                    break;
                case KeyEvent.VK_UP:
                    turnFly(2);
                    break;
                case KeyEvent.VK_SPACE:
                    turnFly(1);
                    break;

            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

    }
    private void deleteLines(){
        for (int y = allConst.HEIGHT - 1; y >= 0; y--)
            while ( FullLine(y))
                dropLine(y);

    }

    private void dropLine(int y){
        for (int my = y - 1; my >= 0; my--)
            for (int x = 0; x < allConst.WIDTH; x++)
                setBoxColor(x, my + 1, getBoxColor(x,my )); // то что ниже копируем то что выше
        for (int x = 0; x < allConst.WIDTH; x++)
            setBoxColor(x, 0, getBoxColor(x,0));

    }


    private boolean FullLine(int y){  //если строчка у полная

        for (int x = 0; x < allConst.WIDTH; x++)
            if (getBoxColor(x,y) !=2)
                return false;
        return true;
    }

    class TimeAdapter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            moveFly(0, 1);
            if (fly.Landed()){
                showFigure(2);
                deleteLines();
                addFigure();
            }
        }
    }


}

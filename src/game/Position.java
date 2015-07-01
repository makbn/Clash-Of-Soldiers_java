/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

public class Position {

    private int x;
    private int y;
    private int last_x;
    private int last_y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int Row) {
        this.x = Row;
    }

    public void setY(int Culomn) {
        this.y = Culomn;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLast_x() {
        return last_x;
    }

    public void setLast_x(int last_x) {
        this.last_x = last_x;
    }

    public int getLast_y() {
        return last_y;
    }

    public void setLast_y(int last_y) {
        this.last_y = last_y;
    }

}

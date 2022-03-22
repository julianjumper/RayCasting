package jmjumper.rayCast.components;

public class Vector {

    private int x, y;

    public Vector ( int x, int y ) {
        this.x = x;
        this.y = y;
    }

    public Vector add ( Vector vec ) {
        this.x += vec.getX();
        this.y += vec.getY();
        return new Vector(x, y);
    }

    public Vector substract ( Vector vec ) {
        int newX = this.x - vec.getX();
        int newY = this.y - vec.getY();
        return new Vector(newX, newY);
    }

    public int length () {
        return (int) Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY());
    }

    public void print () {
        System.out.println("x-Value: " + this.getX());
        System.out.println("y-Value: " + this.getY());
        System.out.println("----------------");
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

    public void setX ( int x ) {
        this.x = x;
    }

    public void setY ( int y ) {
        this.y = y;
    }

}

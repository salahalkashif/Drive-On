package Pages;

public class GenertingCars {
    public double x;
    public double y;
    public int increase = 0;
    public boolean isturn = true;


    GenertingCars(double xPosition,double yPosition){
        this.x=xPosition;
        this.y= yPosition;
    }
    public void decY(){
        y--;
    }

    public boolean checkY(){
        return y <= -15;
    }

    @Override
    public String toString() {
        return "GenertingCars{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

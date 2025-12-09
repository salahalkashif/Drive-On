package Pages;

import DriveOn.*;

import javax.swing.*;

public class Accident implements Variables {

    public static int xCarMain = 54;
    public static int yCarMain = 5;
    boolean isAccident = false;

    public void accident(AllCars allCars) {
        if (allCars.NumberOfCarsRandom > 0) {
            for (int i = 0; i < allCars.cars.length; i++) {
                collision(allCars.cars[i].x, allCars.cars[i].y);

            }
        }
    }

    public void collision ( double xCar, double yCar){
        DriveOnGLEventListener3.AccidentSound.loadSound("Bomb.wav");
        if (((yCar == yCarMain + 17) || (yCar == yCarMain + 16)) && ((xCarMain + 7 >= xCar)) && (xCarMain - 8 <= xCar)) {
            handleAccident();
        } else if ((xCar == xCarMain + 6) && ((yCarMain - 15 <= yCar)) && (yCarMain + 16 >= yCar)) {
            handleAccident();
        } else if ((xCar == xCarMain - 7) && ((yCarMain - 15 <= yCar)) && (yCarMain + 16 >= yCar)) {
            handleAccident();
        } else if ((yCar == yCarMain - 16) && ((xCarMain + 7 >= xCar)) && (xCarMain - 8 <= xCar)) {
            handleAccident();
        }
    }

    private void handleAccident() {
        isAccident = true;
        DriveOnGLEventListener3.AccidentSound.playSound();
        MainMenu.lives--;
        if (MainMenu.lives <= 0) {
            MainMenu.Page = 20; // Show the lose screen
        } else {
            // Reset car position after a collision
            xCarMain = 54;
            yCarMain = 5;
        }
    }
}

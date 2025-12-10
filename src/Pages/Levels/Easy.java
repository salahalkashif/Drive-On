package Pages.Levels;

import DriveOn.Variables;
import DriveOn.*;
import Pages.*;
import com.sun.opengl.util.GLUT;

import javax.media.opengl.GL;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;

public class Easy implements KeyListener, Variables {
    GLUT glut = new GLUT();
    AllCars allCars = new AllCars();
    Accident accident = new Accident();
    Lives lives = new Lives();
    Score score = new Score();
    Pages.Time time = new Pages.Time();

    public void start(GL gl){
        // First, check for win/loss conditions to stop the game.
        if (Score.score >= 15) {
            MainMenu.Page = 23; // Set page to "Win" (Document.png)
            return; // Stop game logic and let the main loop render the win screen.
        }

        if (!lives.pause) { // lives.pause becomes false when lives run out.
            MainMenu.Page = 20; // Set page to "Lose" (x.png)
            return; // Stop game logic and let the main loop render the lose screen.
        }

        // If the game is still running, execute the game logic and drawing.
        accident.accident(allCars);
        lives.lives(gl, accident, allCars);

        if (!DriveOnGLEventListener3.ChangeLane) {
            allCars.DrawMainCars(gl, Accident.xCarMain, Accident.yCarMain, 0, 1);
        }
        if (DriveOnGLEventListener3.ChangeLane)
        {
            allCars.DrawMainCars(gl, Accident.xCarMain, Accident.yCarMain, DriveOnGLEventListener3.CL, 1);
            DriveOnGLEventListener3.CL=0;
        }
        allCars.DrawCarsRandom(gl, score,true);
        Pages.Time.drawTime(gl, -0.98f, 0.90f);
        score.drawScore(gl);
    }

    public void handleKeyPress() {
        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            DriveOnGLEventListener3.ChangeLane=true;
            if (Accident.xCarMain > 17) {
                DriveOnGLEventListener3.CL=2;
                Accident.xCarMain--;
            }
        }
        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            DriveOnGLEventListener3.ChangeLane=true;
            if (Accident.xCarMain < 75) {
                DriveOnGLEventListener3.CL=1;
                Accident.xCarMain++;
            }
        }
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (Accident.yCarMain > 3) {
                Accident.yCarMain--;
            }
        }
        if (isKeyPressed(KeyEvent.VK_UP)) {
            if (Accident.yCarMain < (100-13)) {
                Accident.yCarMain++;
            }
        }
    }

    public BitSet keyBits = new BitSet(256);

    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyBits.set(keyCode);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyBits.clear(keyCode);
    }

}

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
    Lost lost = new Lost();
    Won won =new Won();
    HighScore highScore = new HighScore();

    public void start(GL gl){
        if (lives.pause) {
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

        }else if(!won.equals(Pages.Time.scoreString)){
            System.out.println(won.equals(Pages.Time.scoreString) + "    " + won + "   " + Pages.Time.scoreString);
            MainMenu.Page=20;
            lost.DrawLost(gl);
            time.drawendTime(gl, -0.1f, 0.18f, Pages.Time.endTime);
            score.drawendScore(gl,-0.2f, -0.01f);
            highScore.drawHighScore(gl, -0.00f, -0.01f);
            drawusername(gl);
        }
        if(won.equals(Time.scoreString)) {
            lives.pause = false;
            MainMenu.Page=23;
            won.DrawWin(gl);
            score.drawendScore(gl,-0.1f, -0.01f);
            highScore.drawHighScore(gl,-0.1f, 0.18f);
            drawusername(gl);
        }
    }
    private void drawusername(GL gl) {

        gl.glRasterPos2f(-0.1f, -0.19f);


        String scoreString = "user : " + MainMenu.username;
        for (char c : scoreString.toCharArray()) {
            glut.glutBitmapCharacter(GLUT.BITMAP_HELVETICA_18, (char) c);
        }
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

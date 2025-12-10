package Pages ;




import DriveOn.*;

import javax.media.opengl.GL;
import javax.swing.*;

public class MainMenu implements Variables {
    public static int Page = 13;
    public static boolean play = false;
    static boolean isEasy;
    static boolean isMulti;
    public static String username;
    static String username1;
    static String username2;
    AllCars allCars=new AllCars();
    public static int gameMode = 0; //1 for easy, 2 for hard, 3 for multi
    public static int lives = 3;

    public void DrawMainMenu(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, indexImg[Page]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }

    public void positions(int X, int Y) {
        if ((X >= 47 && X <= 238) && (Y >= 62 && Y <= 101) && Page == 13)//play button
        {
            Page = 15;
            username= JOptionPane.showInputDialog("Enter user name");
        } else if ((X >= 367 && X <= 612) && (Y >= 175 && Y <= 219) && Page == 15)//easy button
        {
            play = true;
            isEasy = true;
            gameMode = 1;
        } else if ((X >= 0 && X <= 107) && (Y >= 0 && Y <= 105) && Page == 16) {
            Page = 13;
        }//back multiplayer
        else if ((X >= 0 && X <= 107) && (Y >= 0 && Y <= 105) && Page == 15) {
            Page = 13;
        }//back play
        else if ((X >= 283 && X <= 473) && (Y >= 64 && Y <= 100) && Page == 13)//Multiplayer button
        {
            Page = 16;//
        } else if ((X >= 0 && X <= 107) && (Y >= 0 && Y <= 105) && Page == 14)//back button
        {
            Page = 13;
        } else if ((X >= 514 && X <= 707) && (Y >= 65 && Y <= 99)&&Page==13) {
            Page = 14;
        }//how to play

        else if ((X >= 748 && X <= 946) && (Y >= 62 && Y <= 103) && Page == 13)//exit button
        {
            System.exit(0);
        }else if ((X >= 370 && X <= 613) && (Y >= 247 && Y <= 291) && Page == 15)//hard button
        {
            isEasy = false;
            play = true;
            gameMode = 2;
        }else if ((X >= 256 && X <= 730) && (Y >= 286 && Y <= 358)&& Page==16) {
            username1= JOptionPane.showInputDialog("Enter user 1 name ");
            username2= JOptionPane.showInputDialog("Enter user 2 name ");
            isMulti = true;
            play = true;
            gameMode = 3;
            Page=22;
        }//vs player2
        if (X>=386&&X<=595&&Y>=341&&Y<=422&&Page==17){
            //resume
            DriveOnGLEventListener3.flagPause = false;
            Page=15;
        }
        if (X>=859&&X<=930&&Y>=13&&Y<=81&&(Page==15||Page==16)){
            //icon
            DriveOnGLEventListener3.flagPause=true;
            Page=17;
        }
        if (X>=388&&X<=598&&Y>=513&&Y<=601&&Page==17){
            //pause menu back to menu
            play=false;
            DriveOnGLEventListener3.flagPause = false;
            gameMode = 0;
            Accident.xCarMain = 54;
            Accident.yCarMain = 5;
            AllCars.reset();
            Time.time = 0;
            Score.score = 0;
            Time.scoreString="";
            Page=13;
            lives = 3;
        }
        if (X>=368&&X<=602&&Y>=653&&Y<=736&&Page==20){
            //main menu button when you lose
            play=false;
            gameMode = 0;
            Accident.xCarMain = 54;
            Accident.yCarMain = 5;
            AllCars.reset();
            Time.time = 0;
            Score.score = 0;
            Time.scoreString="";
            Page=13;
            lives = 3;

        }
        if (X>=368&&X<=602&&Y>=653&&Y<=736&&Page==23){
            //main menu button when you win
            play=false;
            gameMode = 0;
            Accident.xCarMain = 54;
            Accident.yCarMain = 5;
            AllCars.reset();
            Time.time = 0;
            Score.score = 0;
            Time.scoreString="";
            Page=13;
            lives = 3;

        }else if ((X >= 264 && X <= 728) && (Y >= 174 && Y <= 237)&& Page==16) {
            play=true;
            gameMode=4;
        }//vs ai

        if (X >= 334 && X <= 635 && Y >= 736 && Y <= 835 && Page == 20) {
            play = false;
            gameMode = 0;
            Accident.xCarMain = 54;
            Accident.yCarMain = 5;
            AllCars.reset();
            Time.time = 0;
            Score.score = 0;
            Time.scoreString = "";
            Page = 13;
            lives = 3;
        }
    }
}

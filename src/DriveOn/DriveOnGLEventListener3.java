package DriveOn;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Pages.*;
import Pages.Levels.Easy;
import Pages.Levels.Hard;
import Texture.TextureReader;
import com.sun.opengl.util.GLUT;

import java.awt.event.*;
import java.io.IOException;
import javax.media.opengl.*;

import javax.media.opengl.glu.GLU;

public class DriveOnGLEventListener3 extends DriveOnListener implements Variables, MouseListener {
    int y = 100;

    public static boolean ChangeLane=false;
    public static boolean ChangeLane1=false;
    public static int CL=0;
    public static int CL1=0;
    MainMenu menus = new MainMenu();
    Easy easy = new Easy();
    Hard hard = new Hard();
    Multi multi = new Multi();
    Pause pause=new Pause();
    Score score = new Score();
    GLUT glut = new GLUT();
    public static boolean flagPause = false;
    static SoundPlayer GameSound = new SoundPlayer();
    public static SoundPlayer AccidentSound  = new SoundPlayer();
    AI ai = new AI();
    
    // Removed GLU object as a member


    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(imgs.length, indexImg, 0);
        GameSound.loadSound("alex-productions-get-going.wav");
        GameSound.MainMusic();
        for(int i = 0; i < imgs.length; i++){
            try {
                gameTexture[i] = TextureReader.readTexture(assetsFolderName + "//" + imgs[i] , true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, indexImg[i]);
//                mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        gameTexture[i].getWidth(), gameTexture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        gameTexture[i].getPixels() // Imagedata
                );
            } catch( IOException e ) {
                System.out.println(e);
                e.printStackTrace();
            }
        }

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();

        if (MainMenu.Page == 20) { // Lose screen
            DrawFullScreenImage(gl, 20); // Draw x.png
            drawEndGameText(gl); // Reverted to original call
        } else if (MainMenu.Page == 23) { // Win screen
            DrawFullScreenImage(gl, 23); // Draw Document.png
            drawEndGameText(gl); // Reverted to original call
        } else if(!menus.play&&!flagPause) {
            menus.DrawMainMenu(gl);
        }
        else if(!flagPause && MainMenu.gameMode == 1) {
            DrawBackground(gl,y-100);
            DrawBackground(gl,y);
            DrawBackground(gl,y+100);
            pause.DrawPauseMenu(gl,86,90);
            DrawBoard(gl);
            easy.start(gl);
            easy.handleKeyPress();
            if(y>0){
                y-=2;
            }else {
                y=100;
                y-=2;
            }
        }else if(!flagPause && MainMenu.gameMode == 2){
            DrawBackground(gl,y-100);
            DrawBackground(gl,y);
            DrawBackground(gl,y+100);
            pause.DrawPauseMenu(gl,86,90);
            DrawBoard(gl);
            hard.start(gl);
            hard.handleKeyPress();
            if(y>0){
                y-=2;
            }else {
                y=100;
                y-=2;
            }
        } else if (!flagPause && MainMenu.gameMode == 3) {
            DrawBackground(gl,y-100);
            DrawBackground(gl,y);
            DrawBackground(gl,y+100);
            pause.DrawPauseMenu(gl,86,90);
            DrawBoard(gl);
            multi.start(gl);
            multi.handleKeyPress();
            if(y>0){
                y-=2;
            }else {
                y=100;
                y-=2;
            }
        }else if(!flagPause && MainMenu.gameMode == 4) {
            DrawBackground(gl, y - 100);
            DrawBackground(gl, y);
            DrawBackground(gl, y + 100);
            pause.DrawPauseMenu(gl, 86, 90);
            DrawBoard(gl);
            ai.start(gl);
            ai.handleKeyPress();
            if (y > 0) {
                y -= 2;
            } else {
                y = 100;
                y -= 2;
            }
        }
        if(flagPause){
            DrawBackground(gl,y-100);
            DrawBackground(gl,y);
            DrawBackground(gl,y+100);
            pause.DrawPause(gl,45,45);
        }
    }

    private void drawEndGameText(GL gl) {
        // Reverted to simpler OpenGL state management for text drawing
        gl.glColor3f(0.0f, 0.0f, 0.0f); // Set color to black

        // Draw Username at converted OpenGL coordinates for (221, 555) on a 1000x1000 screen, then lowered by 2 "degrees" (0.04f)
        // Original x_gl = -0.558f, Original y_gl = -0.11f
        // New y_gl = -0.11f - 0.04f = -0.15f
        gl.glRasterPos2f(-0.558f, -0.15f);
        String userString =  MainMenu.username;
        glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, userString);

        // Draw Score at converted OpenGL coordinates for (713, 579) on a 1000x1000 screen, then raised by 1 "degree" (0.02f)
        // Original x_gl = 0.426f, Original y_gl = -0.158f
        // New y_gl = -0.158f + 0.02f = -0.138f
        gl.glRasterPos2f(0.426f, -0.138f);
        String scoreString =  "" + score.score;
        glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, scoreString);

        gl.glColor3f(1.0f, 1.0f, 1.0f); // Reset color to white for other drawings
    }

    public void DrawFullScreenImage(GL gl, int imageIndex) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, indexImg[imageIndex]);
        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
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

    public void DrawBackground(GL gl,int y ){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, indexImg[imgs.length-1]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated( 0, y/(100/2.0) - 0.9, 0);

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

    public void DrawBoard(GL gl){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, indexImg[imgs.length-2]);    // Turn Blending On

        gl.glPushMatrix();

        // Front Face
        gl.glTranslated( -0.85f, 0.85, 0);
        gl.glScaled(0.15, 0.25, 1);
        gl.glBegin(GL.GL_QUADS);
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



    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println(x+" "+y);
        menus.positions(x, y);
    }


    @Override
    public void mouseReleased(MouseEvent e) {

    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }


    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {


    }
}

package DriveOn;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package project;

import com.sun.opengl.util.*;
import java.awt.*;
import javax.media.opengl.*;
import javax.swing.*;

public class DriveOn extends JFrame {

    public static void main(String[] args) {
        new DriveOn();
    }


    public DriveOn() {
        GLCanvas glcanvas;
        Animator animator;
        
        DriveOnGLEventListener3 listener = new DriveOnGLEventListener3();
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener.easy);
        glcanvas.addKeyListener(listener.hard);
        glcanvas.addKeyListener(listener.multi);
        glcanvas.addKeyListener((listener.ai));
        glcanvas.addMouseListener(listener);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        animator = new FPSAnimator(24);
        animator.add(glcanvas);
        animator.start();

        setTitle("Drive On");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
    }
}

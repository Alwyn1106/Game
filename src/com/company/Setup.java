package com.company;

import javax.swing.*;
import java.awt.*;

public class Setup extends Canvas {

    public Setup(){


        Logic screen = new Logic();
        JFrame frame = new JFrame();
        frame.add(screen);
        frame.pack();
        frame.setTitle(screen.getTITLE());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screen.getWIDTH(), screen.getHEIGHT());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        System.out.println("Running....");
        screen.setStart();


    }


}

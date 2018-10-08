package cz.uhk.fim.rssreader.gui;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame(){
        init();
    }

    private void init(){
        setTitle("RSS reader");
        setSize(800,640);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}

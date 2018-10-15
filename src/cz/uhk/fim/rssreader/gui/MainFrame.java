package cz.uhk.fim.rssreader.gui;

import cz.uhk.fim.rssreader.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainFrame extends JFrame {

    public MainFrame(){
        init();
    }

    private void init(){
        setTitle("RSS reader");
        setSize(800,640);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initContent();
    }

    private void initContent(){
        JPanel panel = new JPanel(new BorderLayout());

        JButton buttonLoad = new JButton("Load");

        JTextField textField = new JTextField();
        JButton buttonSave = new JButton("Save");
/**
 * TODO: pridat listenery na load a save, napsat metodu boolean validateInput - je prazdny?, pridat JLabel lblError - bude cervenej - zobrazi se v pripade chyby
 *
 * */
        panel.add(buttonLoad,BorderLayout.WEST);
        panel.add(textField,BorderLayout.CENTER);
        panel.add(buttonSave,BorderLayout.EAST);
        add(panel,BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        add(new JScrollPane(textArea),BorderLayout.CENTER);

        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    textArea.setText(FileUtils.loadStringFromFile(textField.getText()));
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileUtils.saveStringToFile(textField.getText(), textArea.getText().getBytes());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }


}

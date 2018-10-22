package cz.uhk.fim.rssreader.gui;

import cz.uhk.fim.rssreader.model.RSSItem;
import cz.uhk.fim.rssreader.utils.FileUtils;
import cz.uhk.fim.rssreader.utils.RSSList;
import cz.uhk.fim.rssreader.utils.RSSParser;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainFrame extends JFrame {

    private static final String VALIDATION_TYPE = "VALIDATION_TYPE";
    private static final String IO_LOAD_TYPE = "IO_LOAD_TYPE";
    private static final String IO_SAVE_TYPE = "IO_SAVE_TYPE";

    private JLabel lblErrorMessage;
    private JTextField textField;
    private RSSList rssList;

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

        textField = new JTextField();
        JButton buttonSave = new JButton("Save");
        lblErrorMessage = new JLabel();
        lblErrorMessage.setForeground(Color.RED);
        lblErrorMessage.setHorizontalAlignment(SwingConstants.CENTER);
        lblErrorMessage.setVisible(false);

        panel.add(buttonLoad,BorderLayout.WEST);
        panel.add(textField,BorderLayout.CENTER);
        panel.add(buttonSave,BorderLayout.EAST);
        panel.add(lblErrorMessage, BorderLayout.SOUTH);

        add(panel,BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        add(new JScrollPane(textArea),BorderLayout.CENTER);

        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()){
                    try {
                        textArea.setText(FileUtils.loadStringFromFile(textField.getText()));
                        lblErrorMessage.setVisible(false);
                    } catch (IOException ex) {
                        showErrorMessage(IO_LOAD_TYPE);
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                   /* try {
                        FileUtils.saveStringToFile(textField.getText(), textArea.getText().getBytes());
                        lblErrorMessage.setVisible(false);
                    } catch (IOException e1) {
                        showErrorMessage(IO_SAVE_TYPE);
                        e1.printStackTrace();
                    }*/

                    try {
                        rssList = new RSSParser().getParsedRSS(textArea.getText());
                        textArea.setText("");
                        for (RSSItem item : rssList.getAllItems()){
                            textArea.append(String.format("%s - autor: %s%n",item.getTitle(),item.getAutor()));
                        }

                    } catch (IOException | ParserConfigurationException | SAXException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private void showErrorMessage(String type) {
        String message;
        switch(type){
            case VALIDATION_TYPE:
                message = "Zadávací pole nemůže být prázdné!";
                break;
            case IO_LOAD_TYPE:
                message = "Chyba při načítání souboru!";
                break;
            case IO_SAVE_TYPE:
                message = "Chyba při ukládání souboru!";
                break;
            default:
                message = "Bůh ví, co se stalo";
                break;
        }
        lblErrorMessage.setText(message);
        lblErrorMessage.setVisible(true);
    }

    private boolean validateInput(){
        lblErrorMessage.setVisible(false);
        if(textField.getText().trim().isEmpty()) {
            showErrorMessage(VALIDATION_TYPE);
            return false;
        }
        return true;
    }


}

package cz.uhk.fim.rssreader.gui;

import cz.uhk.fim.rssreader.model.RSSItem;
import cz.uhk.fim.rssreader.model.RSSSource;
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
import java.util.List;

public class MainFrame extends JFrame {

    private static final String VALIDATION_TYPE = "VALIDATION_TYPE";
    private static final String IO_LOAD_TYPE = "IO_LOAD_TYPE";
    private static final String IO_SAVE_TYPE = "IO_SAVE_TYPE";

    private JLabel lblErrorMessage;
    private List<RSSSource> sources;
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
        JComboBox combo;
        lblErrorMessage = new JLabel();
        lblErrorMessage.setForeground(Color.RED);
        lblErrorMessage.setHorizontalAlignment(SwingConstants.CENTER);
        lblErrorMessage.setVisible(false);

        try {
            sources = FileUtils.loadSources();
            combo = new JComboBox(sources.toArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        JButton btnAdd = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");

        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.add(btnAdd,BorderLayout.WEST);
        btnPanel.add(btnEdit,BorderLayout.CENTER);
        btnPanel.add(btnDelete,BorderLayout.EAST);

        panel.add(combo,BorderLayout.NORTH);
        panel.add(btnPanel,BorderLayout.CENTER);
        panel.add(lblErrorMessage, BorderLayout.SOUTH);

        add(panel,BorderLayout.NORTH);



        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SourceDialog(sources,-1);
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SourceDialog(sources,combo.getSelectedIndex());
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sources.remove(combo.getSelectedIndex());
            }
        });

        JPanel content = new JPanel(new WrapLayout());
        try {
            rssList = new RSSParser().getParsedRSS("https://www.zive.cz/rss/sc-47/");

            for(RSSItem item : rssList.getAllItems()){
                content.add(new CardView(item));
            }
        } catch (IOException | SAXException | ParserConfigurationException e1) {
            showErrorMessage(IO_LOAD_TYPE);
            System.out.println(e1.getMessage());
        }
        add(new JScrollPane(content),BorderLayout.CENTER);
/*
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<RSSSource> sources = new ArrayList<>();

            }
        });

        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<RSSSource> sources = FileUtils.loadSources();
                    for (RSSSource source :sources){
                        System.out.println(source.getName()+"-"+source.getSource());
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });*/

        //add(new JScrollPane(textArea),BorderLayout.CENTER);

       /* buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()){
                   /* try {
                        textArea.setText(FileUtils.loadStringFromFile(textField.getText()));

                    } catch (IOException ex) {

                    }*/

            /*        try {
                        lblErrorMessage.setVisible(false);
                        rssList = new RSSParser().getParsedRSS(textField.getText());
                        textArea.setText("");
                        for(RSSItem item : rssList.getAllItems()){
                            textArea.append(String.format("%s - autor: %s%n", item.getTitle(), item.getAuthor()));
                            System.out.println(item.getDescription());
                            System.out.println("-");
                        }
                    } catch (IOException | SAXException | ParserConfigurationException e1) {
                        showErrorMessage(IO_LOAD_TYPE);
                        System.out.println(e1.getMessage());
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


/*
                }
            }
        });*/
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
/*
    private boolean validateInput(){
        lblErrorMessage.setVisible(false);
        if(textField.getText().trim().isEmpty()) {
            showErrorMessage(VALIDATION_TYPE);
            return false;
        }
        return true;
    }
*/

}

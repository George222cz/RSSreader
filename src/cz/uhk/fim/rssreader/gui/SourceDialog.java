package cz.uhk.fim.rssreader.gui;

import cz.uhk.fim.rssreader.model.RSSSource;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SourceDialog extends JFrame {

    List<RSSSource> sources;

    public SourceDialog(List<RSSSource> sources, int index)  {
        this.sources = sources;
        init(index);
    }

    private void init(int index) {
        setLayout(new WrapLayout());
        setSize(520,340);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        setTitle("přidat nový src");

        JLabel nazevLabel = new JLabel();
        nazevLabel.setText("Název:");

        JTextField nazevTF = new JTextField();

        JLabel linkLabel = new JLabel();
        nazevLabel.setText("Link:");

        JTextField linkTF = new JTextField();

        JPanel btnPanel = new JPanel(new BorderLayout());
        JButton btnOK = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");
        btnPanel.add(btnOK,BorderLayout.WEST);
        btnPanel.add(btnCancel,BorderLayout.EAST);

        add(nazevLabel);
        add(nazevTF);
        add(linkLabel);
        add(linkTF);
        add(btnPanel);

        if(index != -1){
            setTitle("upravit src");
            nazevTF.setText(sources.get(index).getName());
            linkTF.setText(sources.get(index).getSource());



        }

    }


}

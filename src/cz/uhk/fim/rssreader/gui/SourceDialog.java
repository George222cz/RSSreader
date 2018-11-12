package cz.uhk.fim.rssreader.gui;

import cz.uhk.fim.rssreader.model.RSSSource;
import cz.uhk.fim.rssreader.utils.FileUtils;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SourceDialog extends JFrame {

    private static final int COMPONENT_WIDTH = 360;

    List<RSSSource> sources;

    public SourceDialog(List<RSSSource> sources, int index)  {
        this.sources = sources;
        init(index);
    }

    private void init(int index) {
        setLayout(new BorderLayout());
        setSize(420,240);
        setLocationRelativeTo(null);
        //setUndecorated(true);
        setVisible(true);
        setTitle("přidat nový src");

        JLabel nazevLabel = new JLabel();
        nazevLabel.setText("Název:");
        JPanel p1 = new JPanel(new BorderLayout());
        p1.add(nazevLabel,BorderLayout.NORTH);

        JTextField nazevTF = new JTextField();
        p1.add(nazevTF,BorderLayout.SOUTH);
        p1.setBorder(new CompoundBorder(p1.getBorder(),new EmptyBorder(10,0,0,0)));

        JPanel p2 = new JPanel(new BorderLayout());
        JLabel linkLabel = new JLabel();
        linkLabel.setText("Link:");

        JTextField linkTF = new JTextField();
        p2.add(linkLabel,BorderLayout.NORTH);
        p2.add(linkTF,BorderLayout.SOUTH);
        p2.setBorder(new CompoundBorder(p2.getBorder(),new EmptyBorder(10,0,50,0)));

        JPanel btnPanel = new JPanel(new BorderLayout());
        JButton btnOK = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");
        btnPanel.add(btnOK,BorderLayout.WEST);
        btnPanel.add(btnCancel,BorderLayout.EAST);
        btnPanel.setBorder(new CompoundBorder(btnPanel.getBorder(),new EmptyBorder(20,0,10,0)));

        add(p1,BorderLayout.NORTH);
        add(p2,BorderLayout.CENTER);
        add(btnPanel,BorderLayout.SOUTH);

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(index == -1) {
                    sources.add(new RSSSource(nazevTF.getText(), linkTF.getText()));
                }else {
                    sources.get(index).setName(nazevTF.getText());
                    sources.get(index).setSource(linkLabel.getText());
                }
                FileUtils.saveSources(sources);
                dispose();
            }
        });

        if(index != -1){
            setTitle("upravit src");
            nazevTF.setText(sources.get(index).getName());
            linkTF.setText(sources.get(index).getSource());
        }

    }


}

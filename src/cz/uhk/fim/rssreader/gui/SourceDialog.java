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

    private List<RSSSource> sources;

    public SourceDialog(){}

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
        p1.setBorder(new CompoundBorder(p1.getBorder(),new EmptyBorder(10,5,0,5)));

        JPanel p2 = new JPanel(new BorderLayout());
        JLabel linkLabel = new JLabel();
        linkLabel.setText("Link:");

        JTextField linkTF = new JTextField();
        p2.add(linkLabel,BorderLayout.NORTH);
        p2.add(linkTF,BorderLayout.SOUTH);
        p2.setBorder(new CompoundBorder(p2.getBorder(),new EmptyBorder(10,5,50,5)));

        JPanel btnPanel = new JPanel(new BorderLayout());
        JButton btnOK = new JButton("   OK   ");
        JButton btnCancel = new JButton("Cancel");
        btnPanel.add(btnOK,BorderLayout.WEST);
        btnPanel.add(btnCancel,BorderLayout.EAST);
        btnPanel.setBorder(new CompoundBorder(btnPanel.getBorder(),new EmptyBorder(20,125,10,125)));

        JLabel errorLabel = new JLabel();
        errorLabel.setText("Zadávací pole nemůže být prázdné!");
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setVisible(false);

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(p1,BorderLayout.NORTH);
        mainPanel.add(p2,BorderLayout.CENTER);
        mainPanel.add(errorLabel,BorderLayout.SOUTH);

        add(mainPanel,BorderLayout.NORTH);
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
                String name = nazevTF.getText().replaceAll(";","");
                String link = linkTF.getText().replaceAll(";","");
                if(validateInput(name) && validateInput(link)) {
                    if (index == -1) {
                        sources.add(new RSSSource(name, link));
                    } else {
                        sources.get(index).setName(name);
                        sources.get(index).setSource(link);
                    }
                    FileUtils.saveSources(sources);
                    dispose();
                }else{
                    errorLabel.setVisible(true);
                }
            }
        });

        if(index != -1){
            setTitle("upravit src");
            nazevTF.setText(sources.get(index).getName());
            linkTF.setText(sources.get(index).getSource());
        }

    }

    private boolean validateInput(String input){
        return !input.trim().isEmpty();
    }

}

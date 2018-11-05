package cz.uhk.fim.rssreader.gui;

import cz.uhk.fim.rssreader.model.RSSItem;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DetailFrame extends JFrame {

    private static final int COMPONENT_WIDTH = 360;
    private static final int HEIGHT = 1;

    private final String startHtml = "<html><p style='width: "+COMPONENT_WIDTH+" px'>";
    private final String endHtml = "</p></html>";

    public DetailFrame(RSSItem item)  { init(item);  }

    private void init(RSSItem item) {
        setLayout(new WrapLayout());
        setSize(520,340);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);

        setTitle(item.getTitle());
        setDescription(item.getDescription());
        setLink(item.getLink());
        setAdditionalInfo(String.format("%s - %s",item.getAuthor(),item.getPubDate()));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)){
                    dispose();
                }
            }
        });
    }

    public void setTitle(String title) {
        JLabel titleLabel = new JLabel();
        titleLabel.setText(String.format("%s%s%s",startHtml,title,endHtml));
        titleLabel.setSize(COMPONENT_WIDTH,HEIGHT);
        titleLabel.setFont(new Font("Courier", Font.BOLD,15));
        titleLabel.setBorder(new CompoundBorder(titleLabel.getBorder(),new EmptyBorder(20,0,10,0)));
        add(titleLabel);
    }

    private void setDescription(String description) {
        JLabel descLabel = new JLabel();
        descLabel.setText(String.format("%s%s%s",startHtml,description,endHtml));
        descLabel.setSize(COMPONENT_WIDTH,HEIGHT);
        descLabel.setFont(new Font("Courier", Font.PLAIN,13));
        add(descLabel);
    }

    private void setLink(String link) {
        JLabel linkLabel = new JLabel();
        linkLabel.setText(String.format("%s%s%s",startHtml,link,endHtml));
        linkLabel.setSize(COMPONENT_WIDTH,HEIGHT);
        linkLabel.setFont(new Font("Courier", Font.ITALIC,12));
        linkLabel.setForeground(Color.LIGHT_GRAY);
        add(linkLabel);
    }

    private void setAdditionalInfo(String info) {
        JLabel infoLabel = new JLabel();
        infoLabel.setText(String.format("%s%s%s",startHtml,info,endHtml));
        infoLabel.setSize(COMPONENT_WIDTH,HEIGHT);
        infoLabel.setFont(new Font("Courier", Font.BOLD,11));
        infoLabel.setForeground(Color.LIGHT_GRAY);
        add(infoLabel);
    }
}

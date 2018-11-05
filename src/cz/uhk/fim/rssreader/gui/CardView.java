package cz.uhk.fim.rssreader.gui;

import cz.uhk.fim.rssreader.model.RSSItem;

import javax.swing.*;
import java.awt.*;

public class CardView extends JPanel {

    private static final int ITEM_WIDTH = 180;
    private static final int COMPONENT_WIDTH = 160;
    private static final int HEIGHT = 1;

    private final String startHtml = "<html><p style='width: "+COMPONENT_WIDTH+" px'>";
    private final String endHtml = "</p></html>";

    public CardView(RSSItem item){
        setLayout(new WrapLayout());
        setSize(ITEM_WIDTH,HEIGHT);
        setTitle(item.getTitle());
        setBackground(getColor(item.getTitle()));
        setDescription(item.getDescription());
        setAdditionalInfo(String.format("%s - %s",item.getAuthor(),item.getPubDate()));
    }

    private void setTitle(String title) {
        JLabel titleLabel = new JLabel();
        titleLabel.setText(String.format("%s%s%s",startHtml,title,endHtml));
        titleLabel.setSize(COMPONENT_WIDTH,HEIGHT);
        titleLabel.setFont(new Font("Courier", Font.BOLD,12));
        add(titleLabel);
    }

    private void setDescription(String description) {
        JLabel descLabel = new JLabel();
        descLabel.setText(String.format("%s%s%s",startHtml,description,endHtml));
        descLabel.setSize(COMPONENT_WIDTH,HEIGHT);
        descLabel.setFont(new Font("Courier", Font.PLAIN,11));
        add(descLabel);
    }

    private void setAdditionalInfo(String info) {
        JLabel infoLabel = new JLabel();
        infoLabel.setText(String.format("%s%s%s",startHtml,info,endHtml));
        infoLabel.setSize(COMPONENT_WIDTH,HEIGHT);
        infoLabel.setFont(new Font("Courier", Font.ITALIC,10));
        infoLabel.setForeground(Color.LIGHT_GRAY);
        add(infoLabel);
    }

    private Color getColor(String title){
        String s1 = title.substring(0,title.length()/3);
        String s2 = title.substring(title.length()/3,2*(title.length()/3));
        String s3 = title.substring(2*(title.length()/3));
        return new Color(Math.abs(s1.hashCode()%255), Math.abs(s2.hashCode()%255),Math.abs(s3.hashCode()%255));
    }

}

package cz.uhk.fim.rssreader.gui;

import cz.uhk.fim.rssreader.model.RSSSource;

import javax.swing.*;
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

        if(index != -1){
            sources.get(index).getSource();



        }

    }


}

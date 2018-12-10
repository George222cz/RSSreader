package cz.uhk.fim.rssreader.gui;

import cz.uhk.fim.rssreader.model.RSSItem;
import cz.uhk.fim.rssreader.model.RSSSource;
import cz.uhk.fim.rssreader.utils.FileUtils;
import cz.uhk.fim.rssreader.utils.RSSList;
import cz.uhk.fim.rssreader.utils.RSSParser;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class MainFrame extends JFrame {

    private static final String VALIDATION_TYPE = "VALIDATION_TYPE";
    private static final String IO_LOAD_TYPE = "IO_LOAD_TYPE";
    private static final String IO_SAVE_TYPE = "IO_SAVE_TYPE";
    public static final String FAV_LOAD = "FAV_LOAD";

    private JLabel lblErrorMessage;
    private List<RSSSource> sources;
    private RSSList rssList;
    private SourceDialog sourceDialog = new SourceDialog();

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
        JComboBox combo = new JComboBox();
        lblErrorMessage = new JLabel();
        lblErrorMessage.setForeground(Color.RED);
        lblErrorMessage.setHorizontalAlignment(SwingConstants.CENTER);
        lblErrorMessage.setVisible(false);

        try {
            sources = FileUtils.loadSources();
            combo.setModel(new DefaultComboBoxModel(sources.stream().map(RSSSource::getName).toArray()));

        } catch (IOException e) {
            e.printStackTrace();
        }

        JButton btnAdd = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");
        btnAdd.setPreferredSize(new Dimension(250,1));
        btnDelete.setPreferredSize(new Dimension(250,1));

        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.add(btnAdd,BorderLayout.WEST);
        btnPanel.add(btnEdit,BorderLayout.CENTER);
        btnPanel.add(btnDelete,BorderLayout.EAST);

        panel.add(combo,BorderLayout.NORTH);
        panel.add(btnPanel,BorderLayout.CENTER);
        panel.add(lblErrorMessage, BorderLayout.SOUTH);

        add(panel,BorderLayout.NORTH);

        JPanel content = new JPanel(new WrapLayout());

        combo.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                int index = combo.getSelectedIndex();
                combo.setModel(new DefaultComboBoxModel(sources.stream().map(RSSSource::getName).toArray()));
                combo.setSelectedIndex(index);
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {}

        });

        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rssList = new RSSParser().getParsedRSS(sources.get(combo.getSelectedIndex()).getSource());
                    content.removeAll();
                    for (RSSItem item : rssList.getAllItems()) {
                        content.add(new CardView(item));
                    }
                    content.revalidate();
                    content.repaint();
                    lblErrorMessage.setVisible(false);
                } catch (IOException | ParserConfigurationException | SAXException e1) {
                    if(combo.getSelectedIndex()==0){
                        showErrorMessage(FAV_LOAD);
                    }else{
                        showErrorMessage(IO_LOAD_TYPE);
                        e1.printStackTrace();
                    }
                }
            }
        });

        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                if(combo.getSelectedIndex()==0){
                    content.removeAll();
                    for (RSSItem item : rssList.getAllItems()) {
                        if(item.isFav())
                        content.add(new CardView(item));
                    }
                    content.revalidate();
                    content.repaint();
                }
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!sourceDialog.isVisible()) {
                    sourceDialog = new SourceDialog(sources, -1);
                }
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!sourceDialog.isVisible() && combo.getSelectedIndex()!=0){ sourceDialog = new SourceDialog(sources,combo.getSelectedIndex());}
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(combo.getSelectedIndex()!=0) {
                    sources.remove(combo.getSelectedIndex());
                    FileUtils.saveSources(sources);
                    combo.setSelectedIndex(sources.size()-1);
                }
            }
        });

        try {
            if(sources.size()>1) {
                rssList = new RSSParser().getParsedRSS(sources.get(1).getSource());
                for (RSSItem item : rssList.getAllItems()) {
                    content.add(new CardView(item));
                }
                combo.setSelectedIndex(1);
            }
        } catch (IOException | SAXException | ParserConfigurationException e1) {
            showErrorMessage(IO_LOAD_TYPE);
            System.out.println(e1.getMessage());
        }

        add(new JScrollPane(content),BorderLayout.CENTER);
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
            case FAV_LOAD:
                message = "Nemáte žádné oblíbené.";
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

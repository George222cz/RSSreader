package cz.uhk.fim.rssreader.utils;

import cz.uhk.fim.rssreader.model.RSSItem;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class RSSParser {
    private RSSList rssList;
    private ItemHandler itemHandler;

    public RSSParser(){
        this.rssList = new RSSList();
        this.itemHandler = new ItemHandler(rssList);
    }

    private void parse(String source) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        if(source.contains("http")) {
            parser.parse(new InputSource(new URL(source).openStream()), itemHandler);
        } else if (source.equals(FileUtils.FAV_FILE)){
            parser.parse(new File(source),itemHandler);
        } else {
            parser.parse(new File(source),itemHandler);
        }
    }

    public RSSList getParsedRSS(String source) throws IOException, SAXException, ParserConfigurationException {
        parse(source);
        if(!source.equals(FileUtils.FAV_FILE)) {
            try {
                RSSList rssItems = new RSSParser().getParsedRSS(FileUtils.FAV_FILE);
                for (int i = 0; i < rssList.getAllItems().size(); i++){
                    int finalI = i;
                    boolean isFav = rssItems.getAllItems().stream().map(RSSItem::getTitle).anyMatch(item -> item.equals(rssList.getItem(finalI).getTitle()));
                    rssList.getItem(i).setFav(isFav);
                }
            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }
        }else{
            for (RSSItem item : rssList.getAllItems()){
                item.setFav(true);
            }
        }
        return rssList;
    }
}

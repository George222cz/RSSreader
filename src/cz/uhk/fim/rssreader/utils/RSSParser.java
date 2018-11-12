package cz.uhk.fim.rssreader.utils;

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
        }else {
            parser.parse(new File(source),itemHandler);
        }
    }

    public RSSList getParsedRSS(String source) throws IOException, SAXException, ParserConfigurationException {
        parse(source);
        return rssList;
    }
}

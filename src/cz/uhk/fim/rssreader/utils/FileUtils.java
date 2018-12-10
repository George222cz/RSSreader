package cz.uhk.fim.rssreader.utils;

import cz.uhk.fim.rssreader.model.RSSItem;
import cz.uhk.fim.rssreader.model.RSSSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    private static final String CONFIG_FILE = "config.cfg";
    public static final String FAV_FILE = "FAV.xml";

    public static String loadStringFromFile(String filepath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filepath)));
    }

    public static void saveStringToFile(String filepath, byte[] data) throws IOException {
        Path file = Paths.get(filepath);
        Files.write(file,data);
    }

    public static List<RSSSource> loadSources() throws IOException {
        List<RSSSource> sources = new ArrayList<>();
        new BufferedReader(new StringReader(loadStringFromFile(CONFIG_FILE)))
                .lines().forEach(source -> {
                        String[] parts = source.split(";");
                        sources.add(new RSSSource(parts[0],parts[1]));
                });
        return sources;
    }

    public static void saveSources(List<RSSSource> sources){
        StringBuilder builder = new StringBuilder();
        builder.append(">Oblíbené<;"+FAV_FILE+"\n");
        for(int i = 0; i<sources.size();i++){
            builder.append(String.format("%s;%s",sources.get(i).getName(),sources.get(i).getSource()));
            builder.append(i != sources.size()-1 ? "\n" : "");
        }

        try {
            saveStringToFile(CONFIG_FILE,builder.toString().getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveFav(List<RSSItem> rssItems) {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<rss version=\"2.0\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n" +
                "  <channel>\n" +
                "    <title>Oblíbené</title>\n" +
                "    <link>\n" +
                "    </link>\n" +
                "    <description>\n" +
                "    </description>\n" +
                "    <language>\n" +
                "    </language>\n" +
                "    <pubDate>\n" +
                "    </pubDate>");
        for(int i = 0; i<rssItems.size();i++){
            builder.append("\n" + "    <item>\n");
            builder.append("      <title>"+rssItems.get(i).getTitle()+"</title>\n");
            builder.append("      <link>"+rssItems.get(i).getLink()+"</link>\n");
            builder.append("      <description>"+rssItems.get(i).getDescription()+"</description>\n");
            builder.append("      <pubDate>"+rssItems.get(i).getPubDate()+"</pubDate>\n");
            builder.append("      <dc:creator>"+rssItems.get(i).getAuthor()+"</dc:creator>\n");
            builder.append("    </item>");
        }
        builder.append("\n" + "  </channel>\n" + "</rss>");

        try {
            saveStringToFile(FAV_FILE,builder.toString().getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveFavItem(RSSItem item) throws ParserConfigurationException, SAXException, IOException {
        RSSList rssItems = new RSSParser().getParsedRSS(FAV_FILE);
        rssItems.addItem(item);
        saveFav(rssItems.getAllItems());
        item.setFav(true);
    }

    public static void deleteFavItem(RSSItem item) throws ParserConfigurationException, SAXException, IOException {
        RSSList rssItems = new RSSParser().getParsedRSS(FAV_FILE);
        rssItems.getAllItems().removeIf(i -> i.getTitle().equals(item.getTitle()));
        saveFav(rssItems.getAllItems());
        item.setFav(false);
    }
}

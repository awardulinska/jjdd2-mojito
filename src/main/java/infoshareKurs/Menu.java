package infoshareKurs;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class Menu {
    public static void main(String[] args) {
        System.setProperty("log4j.configurationFile", "log4j.xml");

        Logger logger = LogManager.getLogger("log4j.xml");
        logger.info("This is logger with file appender");
        try {
            FilePath filePath=new FilePath();
            BikeParsing bikeParsing = new BikeParsing(filePath.getFilepath());
            bikeParsing.parseData();
            logger.info("Parsowanie pliku xml");

            MenuText menuText = new MenuText();
            menuText.Text();
            UserChooseFromMenu choose = new UserChooseFromMenu();
            choose.userChooseFromMenu(bikeParsing.getCityList());

            DistanceMath distanceMath = new DistanceMath();
            String placename = "";

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }
}


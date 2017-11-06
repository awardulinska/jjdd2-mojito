
package infoshareKurs.web;

import infoshareKurs.BikeParsing;
import infoshareKurs.City;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet("/portal/cityStat")
public class CityStatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/cityStatGET.jsp");

        final Logger logger = LogManager.getLogger(CityStatServlet.class);
        try {
            final BikeParsing bikeParsing = new BikeParsing("data/nextbike-live.xml");

            Comparator<City> CityComparatorByQuantity = new Comparator<City>() {
                public int compare(City c1, City c2) {
                    if (c1.getPlaceList().size() < c2.getPlaceList().size()) {
                        return 1;
                    } else if (c1.getPlaceList().size() > c2.getPlaceList().size()) {
                        return -1;
                    }
                    return c1.getName().compareTo(c2.getName());
                }
            };

            Comparator<City> CityComparatorByName = new Comparator<City>() {
                public int compare(City c1, City c2) {
                    return c1.getName().compareTo(c2.getName());
                }
            };

            bikeParsing.parseData();
            List<City> cities = bikeParsing.getCityList();

            String orderBy = req.getParameter("orderBy");

            if (orderBy != null && orderBy.equals("name")) {
                Collections.sort(cities, CityComparatorByName);
            } else {
                Collections.sort(cities, CityComparatorByQuantity);
            }

            List<CityPlace> places = new ArrayList<>();
            Integer i = 0;
            for (City city : cities) {
                i++;
                places.add(new CityPlace(city.getName(), city.getPlaceList().size()));
                if (i++ > 100) {
                    break;
                }

                req.setAttribute("places", places);
                req.setAttribute("orderBy", orderBy);

            }
        } catch (
                ParserConfigurationException | SAXException | IOException e) {
            logger.error("blad parsowania pliku");
        }

        requestDispatcher.forward(req, resp);
    }
}


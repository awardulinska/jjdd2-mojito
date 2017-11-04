package infoshareKurs.web;

import infoshareKurs.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@WebServlet("/portal/nearestStation")
public class NearestStationServlet extends HttpServlet {

    @Inject
    Statistics statistics;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/nearestPlaceGET.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final Logger logger = LogManager.getLogger(NearestStationServlet.class);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/nearestPlacePOST.jsp");

        GeoLocation geoLocation = new GeoLocation();
        try {
            req.getSession().setAttribute("formatEx", false);
            if (req.getParameter("latitiudeUser") != null && req.getParameter("longitudeUser") != null) {
                geoLocation.setLatitiudeUser(Double.parseDouble(req.getParameter("latitiudeUser")));

                geoLocation.setLongitudeUser(Double.parseDouble(req.getParameter("longitudeUser")));
            }

            final BikeParsing bikeParsing = new BikeParsing("data/nextbike-live.xml");

            try {
                bikeParsing.parseData();
            } catch (ParserConfigurationException | SAXException | IOException e) {
                logger.error("błąd parsowania pliku xml");
            }
            NearestPlaceFinder nearestPlace = new NearestPlaceFinder(bikeParsing.getCityList());
            Place foundedPlace = nearestPlace.findNearestPlace(geoLocation);
            String toPlace = "";
            toPlace = new StringBuilder()
                    .append(String.valueOf(foundedPlace.getLatitudePlace()))
                    .append(",")
                    .append(String.valueOf(foundedPlace.getLongitudePlace())).toString();

            req.setAttribute("longitudeUser", req.getParameter("latitiudeUser"));
            req.setAttribute("latitiudeUser", req.getParameter("longitudeUser"));
            req.setAttribute("destination", toPlace);
            req.setAttribute("destinationStationName", foundedPlace.getName());

            String cityName = foundedPlace.getCity();
            statistics.add(cityName);

            requestDispatcher.forward(req, resp);
        }
        catch (Exception e) {
            logger.warn("format exeption", e);
            req.getSession().setAttribute("formatEx", true);
            req.getRequestDispatcher("/nearestPlaceGET.jsp").forward(req, resp);
        }
    }
}

package infoshareKurs.web;

import infoshareKurs.BikeParsing;
import infoshareKurs.City;
import infoshareKurs.GetCityStatistics;
import infoshareKurs.Place;

import infoshareKurs.database.beans.CityDAOBeanLocal;
import infoshareKurs.database.entities.CityEntity;
import infoshareKurs.database.entities.CountryEntity;
import org.apache.commons.lang3.StringUtils;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/portal/city_stations")
public class CityStationsServlet extends HttpServlet {

    final Logger logger = LogManager.getLogger(CityStationsServlet.class);

    @Inject
    GetCityStatistics getCityStatistics;

    @Inject
    CityDAOBeanLocal cityDAOBeanLocal;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/CityStationsGET.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final BikeParsing bikeParsing = new BikeParsing( "data/nextbike-live.xml");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/CityStationsPOST.jsp");
        try {
            bikeParsing.parseData();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error("błąd parsowania pliku xml");
        }

        boolean done = false;
        req.setCharacterEncoding("UTF-8");
        String userCity = req.getParameter("userCity");
        logger.info("szukam stacji dla miasta {}, encoded in {}", userCity, req.getCharacterEncoding());
        req.setAttribute("userCity", userCity);
//        req.setAttribute("userCity", req.getParameter("userCity"));

        Integer markerId = 0;
        req.setAttribute("markerId", String.valueOf(markerId));
        while (!done) {
            int i = 0;

            List<Place> allPlaces = new ArrayList<>();

            for (City city : bikeParsing.getCityList()) {
                if (city.getName().equals(userCity)) {
                    i++;
                    for (Place place : city.getPlaceList()) {
                        allPlaces.add(place);
                        logger.debug("wypisanie stacji rowerowych znajdujacych sie danym kraju");
                    }
                    if (i == 1) {
                        String cityName = StringUtils.stripAccents(city.getName());
                        CityEntity cityEntity = new CityEntity();
                        cityEntity.setName(cityName);
                        cityEntity.setNumber(1);
                        cityDAOBeanLocal.addCitiesEntity(cityEntity);
                    }
                    done = true;
                }
            }
            req.setAttribute("places", allPlaces);
            logger.error("wyświetlanie nazwy znalezionego miasta - czy ma polskie znaki");

            if (i == 0) {
                done = true;
            }
            requestDispatcher.forward(req, resp);
        }
    }
}
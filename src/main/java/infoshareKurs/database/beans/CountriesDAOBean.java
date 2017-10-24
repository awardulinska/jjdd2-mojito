package infoshareKurs.database.beans;

import infoshareKurs.database.entities.CitiesEntity;
import infoshareKurs.database.entities.CountriesEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless

public class CountriesDAOBean implements CountriesDAOBeanLocal {

    @PersistenceContext(name = "statistics")
    EntityManager em;

    @Override
    public void addCountriesEntity(CountriesEntity countriesEntity) {
        em.persist(countriesEntity);
        em.flush();
    }

    @Override
    public void updateCountriesEntity(CountriesEntity countriesEntity) {
        em.merge(countriesEntity);
    }

    @Override
    public void deleteCountriesEntity(Integer id) {
        CountriesEntity countriesEntity = em.find(CountriesEntity.class, id);
        if (countriesEntity != null) {
            em.remove(countriesEntity);
        }
    }

    @Override
    public List<String> countryQueryList() {
        List<String> result = new ArrayList<>();
        Query query = em.createQuery("SELECT c.name ,SUM(c.number) FROM CountriesEntity c GROUP BY c.name ORDER BY (sum(c.number)) desc");
        List<Object[]> queryResult = query.getResultList();
        for (Object[] obj : queryResult) {
            result.add(((String) obj[0]) + String.valueOf(obj[1]));
        }
        return result;
    }
}
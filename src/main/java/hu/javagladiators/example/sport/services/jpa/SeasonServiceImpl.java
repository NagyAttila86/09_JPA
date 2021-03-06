package hu.javagladiators.example.sport.services.jpa;

import hu.javagladiators.example.sport.datamodel.Season;
import hu.javagladiators.example.sport.services.GenericDaoServiceImpl;
import hu.javagladiators.example.sport.services.api.SeasonService;
import java.util.HashMap;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author krisztian
 */
@RequestScoped
public class SeasonServiceImpl implements SeasonService{
    @Inject
    GenericDaoServiceImpl dao;
    
    @Override
    public List<Season> getAll() {
        return (List<Season>)dao.getEntities("Season.findAll", new HashMap<String, Object>());
    }

    @Override
    public Season getById(Integer pID) {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("id", pID);
        return (Season)dao.getEntity("Season.findById", params);
    }

    @Override
    public Season getByName(String pName) {
        HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("name", pName);
        return (Season)dao.getEntity("Season.findByName", params);
    }

    @Override
    public void save(Season pSeason) {
        dao.save(pSeason);
    }
    
}

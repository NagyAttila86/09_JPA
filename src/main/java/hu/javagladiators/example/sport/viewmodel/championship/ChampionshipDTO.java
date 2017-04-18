package hu.javagladiators.example.sport.viewmodel.championship;

import hu.javagladiators.example.sport.datamodel.Championship;
import hu.javagladiators.example.sport.datamodel.Round;
import hu.javagladiators.example.sport.datamodel.SportEvent;
import hu.javagladiators.example.sport.resources.admin.ChampionshipREST;
import hu.javagladiators.example.sport.services.api.ChampionshipService;
import hu.javagladiators.example.sport.services.api.ConditionService;
import hu.javagladiators.example.sport.services.api.SeasonService;
import hu.javagladiators.example.sport.services.api.SeriaService;
import hu.javagladiators.example.sport.services.api.SportService;
import hu.javagladiators.example.sport.services.api.SportSpecializationService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author krisztian
 */
@Singleton
public class ChampionshipDTO {
    private static final Logger log = LoggerFactory.getLogger(ChampionshipDTO.class.getSimpleName());
    
    @Inject
    SportService serviceSport;

    @Inject
    SportSpecializationService serviceSportSpecialization;
    
    @Inject
    SeriaService serviceSeria;
    
    @Inject
    SeasonService serviceSeason;

    @Inject
    ConditionService serviceCondition;

    @Inject
    ChampionshipService serviceChampionship;
    
    public ChampionshipDTO() {
    }
    
    
    public ChampionshipWithIDsPOJO factory(Championship pModel){
        ChampionshipWithIDsPOJO res = new ChampionshipWithIDsPOJO();
        res.setName(pModel.getName());
        res.setDescription(pModel.getDescription());
        res.setStartDate(pModel.getStartDate());
        res.setEndDate(pModel.getEndDate());            
        if(pModel.getSeason() != null)
            res.setSeasonid(pModel.getSeason().getId());
        if(pModel.getSeria() != null)
            res.setSeriaid(pModel.getSeria().getId());
        return res;
    }
    
    public Championship toChampionship(ChampionshipWithIDsPOJO pModel, Championship res){
        if(res == null)
            res = new Championship();
        res.setName(pModel.getName());
        res.setDescription(pModel.getDescription());
        res.setStartDate(pModel.getStartDate());
        res.setEndDate(pModel.getEndDate());
        if(pModel.getSeasonid()!= null)
            res.setSeason(serviceSeason.getById(pModel.getSeasonid()));
        if(pModel.getSeriaid()!= null)
            res.setSeria(serviceSeria.getById(pModel.getSeriaid()));
        return res;
    }
    
  
   
    public Set<SportEvent> toSportEvents(ChampionshipSportSpecializationIDPOJO pModel){
        HashSet<SportEvent> res = new HashSet<>();
        SportEvent event;
        int i=0;
        do{
            event= new SportEvent();
            event.setSport(serviceSport.getById(pModel.getSportid()));
            if(pModel.getSpecializationid() != null && pModel.getSpecializationid().size()>i){
                event.setSpecialization(serviceSportSpecialization.getById(pModel.getSpecializationid().get(i)));
            }
            if(pModel.getConditionid() != null && pModel.getConditionid().size()>0)
                for(Integer conditionid: pModel.getConditionid()){
                    event.addCondition(serviceCondition.getById(conditionid));
                }
            
            res.add(event);
            i++;
        }
        while(i<pModel.getSpecializationid().size());
        return res;
    }
    
    public Round toRound(RoundPOJO pModel){
        Round res = new Round();
        res.setName(pModel.getName());
        res.setDescription(pModel.getDescription());
        return res;
    }
}   
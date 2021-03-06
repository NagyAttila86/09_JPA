package hu.javagladiators.example.sport.resources.admin;



import hu.javagladiators.example.sport.datamodel.ConditionType;
import hu.javagladiators.example.sport.services.api.ConditionService;
import hu.javagladiators.example.sport.services.api.SportService;
import hu.javagladiators.example.sport.viewmodel.IdNamePOJO;
import hu.javagladiators.example.sport.viewmodel.system.MessagePOJO;
import hu.javagladiators.example.sport.viewmodel.NameDescriptionPOJO;
import hu.javagladiators.example.sport.viewmodel.BasicEntitiesDTO;
import hu.javagladiators.example.sport.viewmodel.system.PagerPOJO;
import io.swagger.annotations.Api;
import java.util.ArrayList;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * @author krisztian
 */
@Path("/conditiontype")
@Api(value = "Condition Type administration")
public class ConditionTypeREST {
    @Inject
    ConditionService service;

    @Inject
    SportService serviceSport;
    
    @Inject
    BasicEntitiesDTO dtoBasicIdNameDescription;
    
    public ConditionTypeREST() {
    }

     @GET
    @Path("/entity/page") 
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public PagerPOJO entityList(@QueryParam("order") String order, @QueryParam("offset") long offset, @QueryParam("limit") long limit){
        PagerPOJO res = new PagerPOJO();
        res.setTotal(service.getAllConditionType().size());
        List data = new ArrayList();
       for(long i=offset;i<(offset+limit) && i<service.getAllConditionType().size();i++)
            data.add(service.getAllConditionType().get((int)i));
        res.setRows(data);
        return res;
    }
    
    @GET
    @Path("/entity/all")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<ConditionType> entityList(){
        return service.getAllConditionType();
    }

        @GET
    @Path("/idname/all") 
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<IdNamePOJO> idnemeList(){
        List<IdNamePOJO> res = new ArrayList<>();
        List<ConditionType> entities = service.getAllConditionType();
        for(ConditionType entity : entities)
            res.add(IdNamePOJO.factoryDTO(entity));
        return res;
    }

    @GET
    @Path("namedescription/all") 
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<NameDescriptionPOJO> nemedescriptionList(){
        List<NameDescriptionPOJO> res = new ArrayList<>();
        List<ConditionType> entities = service.getAllConditionType();
        for (ConditionType entity : entities) {
            res.add(dtoBasicIdNameDescription.factory(entity));
        }
        return res;
    }
    
    @GET
    @Path("/{id}") 
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public ConditionType findById(@PathParam("id") int id){
        return service.getConditionTypeById(id);
    }

   @POST
    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public MessagePOJO save(NameDescriptionPOJO pData){
        service.save(dtoBasicIdNameDescription.toConditionType(pData));
        return new MessagePOJO("OK");
    }
    
    
}

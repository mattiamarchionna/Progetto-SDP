package gateway.servizi;

import gateway.beans.Nodo;
import gateway.beans.Rete;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("nodes")
public class NodesService {
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getNodesList(){
        return Response.ok(Rete.getInstance()).build();
    }

    @Path("numbers")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getSizeNodesList() {
        return String.valueOf(Rete.getInstance().getNodesList().size());
    }

    @Path("add")
    @POST
    @Consumes({"application/json", "application/xml"})
    public Response addNode(Nodo n) {
        Rete r = Rete.getInstance();
        if(r.existsId(n.getId()))
            return Response.status(Response.Status.FORBIDDEN).build(); // status code = 403. Risorsa già presente
        else {
            r.add(n);
            return Response.ok(r).build();
        }
    }

    @Path("remove/{id}")
    @DELETE
    @Produces({"application/json", "application/xml"})
    public Response removeById(@PathParam("id") int id){
        Rete r = Rete.getInstance();
        for (Nodo n : r.getNodesList()){
            if(n.getId() == id){
                r.remove(n);
                return Response.ok(r).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Path("get/{id}")
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getById(@PathParam("id") int id){
        Nodo n = Rete.getInstance().getById(id);
        if(n != null)
            return Response.ok(n).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Path("update/{id}")
    @PUT
    @Produces({"application/json", "application/xml"})
    public Response updateById(@PathParam("id") int id, Nodo n){
        Rete r = Rete.getInstance();
        int nId = n.getId();

        if(nId != id) {
            if (r.existsId(nId))
                return Response.status(Response.Status.FORBIDDEN).build();
        }

        List<Nodo> listNodes =  r.getNodesList();
        for(int i = 0; i < listNodes.size(); i++){
           if(listNodes.get(i).getId() == id){
               r.update(i, n);
               return Response.ok(r).build();
           }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
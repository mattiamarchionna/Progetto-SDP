package gateway.servizi;

import gateway.beans.Statistica;
import gateway.beans.RegistroStatistiche;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("statistics")
public class StatisticsService {

    @GET
    @Produces({"application/json", "application/xml"})
    public Response getStatisticsList(){
        return Response.ok(RegistroStatistiche.getInstance()).build();
    }

    @Path("add")
    @POST
    @Consumes({"application/json", "application/xml"})
    public Response addStatistic(Statistica s) {
        RegistroStatistiche r = RegistroStatistiche.getInstance();
        r.add(s);
        return Response.ok(r).build();
    }

    @Path("{n}")
    @GET
    @Produces({"application/json", "application/xml"})
    public List<Statistica> getNstatistics(@PathParam("n") int n){
        RegistroStatistiche r = RegistroStatistiche.getInstance();
        return r.getNstatistic(n);
    }

    @Path("/number")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getNumberStatistics(){
        RegistroStatistiche r = RegistroStatistiche.getInstance();
        return String.valueOf(r.getStatisticList().size());
    }

    @Path("media/{n}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMedia(@PathParam("n") int n){
        RegistroStatistiche r = RegistroStatistiche.getInstance();
        return String.valueOf(r.media(n));
    }

    @Path("dev_standard/{n}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getDevStd(@PathParam("n") int n){
        RegistroStatistiche r = RegistroStatistiche.getInstance();
        return String.valueOf(r.dev_standard(n));
    }


}

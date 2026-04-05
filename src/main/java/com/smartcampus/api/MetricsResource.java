@Path("/metrics")
public class MetricsResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMetrics() {
        return Response.ok(MetricsFilter.getEndpointCounts()).build();
    }
}
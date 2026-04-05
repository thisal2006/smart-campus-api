@Provider
public class MetricsFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private static final Map<String, AtomicInteger> endpointCounts = new ConcurrentHashMap<>();

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();
        endpointCounts.computeIfAbsent(path, k -> new AtomicInteger()).incrementAndGet();
    }

    public static Map<String, AtomicInteger> getEndpointCounts() {
        return endpointCounts;
    }
}
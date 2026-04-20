# Smart Campus API

JAX-RS RESTful API for university Smart Campus initiative.

## API Base URL
`http://localhost:8080/api/v1`

## Endpoints

### Discovery
- `GET /` - API metadata and HATEOAS links

### Rooms
- `GET /rooms` - List all rooms
- `POST /rooms` - Create new room
- `GET /rooms/{id}` - Get room by ID
- `DELETE /rooms/{id}` - Delete room (fails if has sensors)

### Sensors
- `GET /sensors` - List all sensors (filter by ?type=)
- `POST /sensors` - Register new sensor
- `GET /sensors/{id}` - Get sensor by ID
- `GET /sensors/{id}/readings` - Get reading history
- `POST /sensors/{id}/readings` - Add new reading

## Sample curl commands

# Discovery
curl -X GET http://localhost:8080/api/v1/

# Create room
curl -X POST http://localhost:8080/api/v1/rooms -H "Content-Type: application/json" -d '{"name":"Lab 101","building":"Engineering","floor":1}'

# Get all rooms
curl -X GET http://localhost:8080/api/v1/rooms

# Create sensor
curl -X POST http://localhost:8080/api/v1/sensors -H "Content-Type: application/json" -d '{"name":"CO2 Monitor","type":"CO2","roomId":1}'

# Filter sensors by type
curl -X GET "http://localhost:8080/api/v1/sensors?type=CO2"

# Add reading to sensor
curl -X POST http://localhost:8080/api/v1/sensors/1/readings -H "Content-Type: application/json" -d '{"value":"420","unit":"ppm"}'

# Get reading history
curl -X GET http://localhost:8080/api/v1/sensors/1/readings

## Report Answers

### Part 1.1 - JAX-RS Lifecycle
The default lifecycle of a JAX-RS resource class is **request-scoped**. A new instance is instantiated for every incoming request. This impacts in-memory data structures because each request gets a fresh instance, so static or singleton-scoped maps/lists must be used with proper synchronization (ConcurrentHashMap) to prevent race conditions and data loss.

### Part 1.2 - HATEOAS Benefits
Hypermedia (HATEOAS) allows clients to navigate the API dynamically through links provided in responses. This benefits client developers by reducing hardcoded URL dependencies, making the API self-documenting and more resilient to URL structure changes.

### Part 2.1 - ID-only vs Full Objects
Returning only IDs reduces network bandwidth significantly when dealing with large collections, but requires clients to make additional requests for details. Returning full objects increases bandwidth usage but reduces client-side processing and round trips. The choice depends on client needs and typical use cases.

### Part 2.2 - DELETE Idempotency
DELETE is idempotent in my implementation. When a client sends the same DELETE request multiple times, the first request removes the resource and returns 204 No Content. Subsequent requests return 404 Not Found (or throw NotFoundException). The server state after multiple identical DELETE calls is the same - the resource no longer exists.


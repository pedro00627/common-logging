# Common Logging Library

A universal logging configuration library for Spring Boot applications, providing standardized, production-ready logging configurations with minimal setup.

## Features

- 🚀 **Zero Configuration** - Works out of the box with sensible defaults
- 📝 **Structured JSON Logging** - Perfect for log aggregation tools
- 🔧 **Multiple Profiles** - Pre-configured for different application types
- 🎯 **Environment-Aware** - Separate configs for dev, prod, and testing
- ⚙️ **Highly Configurable** - Customize via environment variables
- 📊 **Distributed Tracing** - Built-in support for trace/span IDs

## Quick Start

### 1. Add Dependency

#### Gradle
```gradle
implementation 'com.github.pedro00627:common-logging:1.0.3'
```

#### Maven
```xml
<dependency>
    <groupId>com.github.pedro00627</groupId>
    <artifactId>common-logging</artifactId>
    <version>1.0.3</version>
</dependency>
```

### 2. Configure Your Application

Add to your `application.yaml`:

```yaml
logging:
  config: classpath:log4j2-spring.yaml
```

### 3. Set Environment Variables

```bash
export SERVICE_NAME=my-microservice
export APP_PACKAGE=com.mycompany.myapp
```

That's it! 🎉

## Configuration Profiles

### Basic Configuration
- `log4j2-spring.yaml` - Standard configuration for most applications
- `log4j2-dev.yaml` - Development-friendly with colored console output
- `log4j2-prod.yaml` - Production-optimized with file rotation

### Specialized Profiles
- `profiles/web-service.yaml` - For REST APIs and web services
- `profiles/data-service.yaml` - For database-heavy applications
- `profiles/nosql-service.yaml` - For NoSQL and analytics services

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `SERVICE_NAME` | `microservice` | Service identifier in logs |
| `APP_PACKAGE` | `com.example` | Your application's root package |
| `LOG_PATH` | `./logs` | Directory for log files |
| `APP_LOG_LEVEL` | `debug` | Log level for your application |
| `SPRING_LOG_LEVEL` | `info` | Log level for Spring Framework |
| `ROOT_LOG_LEVEL` | `info` | Root logger level |

## Usage Examples

### Web Service with Custom Package
```yaml
logging:
  config: classpath:profiles/web-service.yaml

# Environment variables
SERVICE_NAME: user-api
APP_PACKAGE: com.company.userapi
SECURITY_LOG_LEVEL: debug
```

### Data Service with Database Logging
```yaml
logging:
  config: classpath:profiles/data-service.yaml

# Environment variables
SERVICE_NAME: data-processor
R2DBC_LOG_LEVEL: debug
HIBERNATE_LOG_LEVEL: info
```

### Production Configuration
```yaml
logging:
  config: classpath:log4j2-prod.yaml

# Environment variables
SERVICE_NAME: production-service
LOG_PATH: /var/log/myapp
ROOT_LOG_LEVEL: warn
```

## Log Output Format

### JSON Structure
```json
{
  "timestamp": "2024-09-14T15:30:45.123Z",
  "service": "my-microservice",
  "level": "INFO",
  "traceId": "abc123def456",
  "spanId": "789xyz",
  "logger": "c.c.m.service.UserService",
  "thread": "http-nio-8080-exec-1",
  "message": "User created successfully",
  "mdc": {
    "userId": "12345",
    "operation": "create-user"
  }
}
```

### Development Console Output
```
15:30:45.123 [http-nio-8080-exec-1] INFO  c.c.m.service.UserService - User created successfully
```

## Advanced Configuration

### Custom Logger Configuration
```java
@Component
public class MyService {
    private static final Logger logger = LoggerFactory.getLogger(MyService.class);

    public void processData() {
        MDC.put("operation", "data-processing");
        logger.info("Starting data processing");
        // ... your code
        MDC.clear();
    }
}
```

### Performance Metrics Logging
```java
// For nosql-service profile, use performance logger
private static final Logger metricsLogger =
    LoggerFactory.getLogger("com.mycompany.myapp.performance");

metricsLogger.info("Query execution time: {}ms", duration);
```

## Configuration Details

### File Rotation
- **Development**: Console only
- **Production**:
  - Max file size: 10MB (standard) / 25MB (nosql)
  - Max files retained: 5-20 depending on profile
  - Automatic compression (.gz)

### Logger Hierarchy
- Your application package: `DEBUG` level
- Spring Framework: `INFO` level
- Third-party libraries: `WARN` level
- Database drivers: Configurable per profile

## Integration with Monitoring

This library works seamlessly with:
- **ELK Stack** (Elasticsearch, Logstash, Kibana)
- **Splunk**
- **Grafana + Loki**
- **Datadog**
- **New Relic**

## Contributing

1. Fork the repository
2. Create a feature branch
3. Add tests for new configurations
4. Submit a pull request

## License

MIT License - see [LICENSE](LICENSE) file for details.

---

**Made with ❤️ for the Spring Boot community**
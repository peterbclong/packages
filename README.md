# Packages

A simple RESTful web service with end-points for managing packages of products.

## Quick Start

Build with Maven:

```bash
mvn clean package
```

Run the resulting JAR file:

```bash
java -jar target/packages-0.1.0.jar
```

End-point documentation can then be accessed at:

```http
http://localhost:8080/swagger-ui.html
```

## API

### Create a Package

The request body should represent the package to be created.

### Retrieve a Package

The `id` path variable should contain the identifier of the required package.

### Update a Package

The `id` path variable should contain the identifier of the package to be updated and the request body should represent
the new content of the package.

### Delete a Package

The `id` path variable should contain the identifier of the required package.

### Retrieve All Packages

All packages are retrieved as a list - this may be empty if no packages currently exist.

## Requirements

The project is built with Maven (version 3.2.3 was used in development) and requires Java 1.8.0.


# Standalone Java app with embedded Jetty and REST services

This is a sample app that can be used at least in the following two use cases:
- as a standalone application that exposes REST operations for statistics, administrative and/or monitoring purposes;
- a lightweight microservice that exposes REST operations to the external world, without any dependencies to bigger frameworks like Spring Boot/Cloud.

## App Structure

The main app components are:
- The app starts with `EmbJettyAppStarts`.
- Embedded Jetty specific setup and start is part of `WebAppServer`.

The REST services are implemented using Rest Easy library. `RestApplication` is responsible with the initialization of the `Resource`s that are exposed through REST operations.

At the project root there is also an `/webapp` directory whose structure follows the standard Java Web application structure.

For the scope of this sample, just having an `index.html` file and a `WEB-INF/web.xml` deployment descriptior is more than enough.

## Start & Play

- Run `tech.vision8.embjetty.app.run.EmbJettyAppStart`
- Homepage of the app is available at `http://localhost:8787` whose content is actually the `index.html` file, present in the webapp directory.
- REST services are accessible at `/api/<resource-name>`.
- `Salutation` resource is exposed by two operations:
     - A GET to /text (`http://localhost:8787/api/hello/text`) to get the String/text version
     - A GET to /json (`http://localhost:8787/api/hello/json`) to get the JSON version 

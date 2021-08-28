# projekte-der-wirtschaftsinformatik-shop-backend
Backend for the shop application developed for the university module "Projekte der Wirtschaftsinformatik"

## Projekt Setup
- `git clone https://github.com/florianPat/projekte-der-wirtschaftsinformatik-shop-backend.git`
- `cd projekte-der-wirtschaftsinformatik-shop-backend`
- `git flow init -d`
- `lando start`
- Visit [Shop-Backend](http://pdw-shop-backend.lndo.site:8080/api/greeting)

## Swagger OpenAPI Documentation
- Visit [JSON-View](http://pdw-shop-backend.lndo.site:8080/v2/api-docs)
- Visit [HTML-View](http://pdw-shop-backend.lndo.site:8080/swagger-ui/index.html)

## Mailhog email catcher
- Visit [Mailhog](http://pdw-shop-mailhog.lndo.site/)

## Intellij remote jvm debugging configuration
- Host: pdw-shop-backend.lndo.site
- Port: 5005
- Command line argument for remote jvm: -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005

## Tooling
- `lando compile`: Build project and hot reload code
- `lando test`: Run tests with the h2 in-memory database
- `lando postgresql`: Drop into psql shell
    - `\dt`: Show tables
    - `\d tablename`: Describe table
    - `\q`: exit
- `lando spotbugs`: Run static code analysis
- `lando spotless`: Run code format fixer
- `lando hibernateSchema`: Create hibernate schema file for migration creation
- Flyway migrations run automatically on each server startup (see `lando logs -s appserver -f`)

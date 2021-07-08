# projekte-der-wirtschaftsinformatik-shop-backend
Backend for the shop application developed for the university module "Projekte der Wirtschaftsinformatik"

## Projekt Setup
- `git clone https://github.com/florianPat/projekte-der-wirtschaftsinformatik-shop-backend.git`
- `cd projekte-der-wirtschaftsinformatik-shop-backend`
- `lando start`
- Visit [Shop-Backend](http://pdw-shop-backend.lndo.site:8080/api/greeting)

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

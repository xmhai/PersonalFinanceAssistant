# TODO
- deploy to minicube
- deploy to AKS
- display summary
- pagination
- integrate with keycloak
- admin console (user management/view logs)
- use Kafka
- upload bank statement/credit card/bills pdf files
- set reference to stock transaction

# ISSUE
delete/deactivate job

# Technology Stack
- VS Code
- Eclipse (Spring tools plugin)
- Github Desktop
- React (react-redux, react-route-dom, semantic-ui, ant-design)
- Java (Springboot, lombok)
- Maven
- MySql

# Database
## Naming
- Use underscore with lower case for portability as databases is case-insensitive.
- is_xxxxxx for yes/no column, type unsigned tinyint (mysql)
- Index naming: pk_, uk_, idx_
- Use prefix for database object like vw_xxxxxx, sp_xxxxxx, tr_xxxxxx
- ??? Use two characters prefix for table name to indicate domain, e.g. ct_xxxxxx for code tables
## Design
- Use surrogate key as primary key
- Audit column: created_date, created_by, updated_date, updated_by, is_deleted
- ??? One True Lookup Table (or Code Table)  
    although many articles oppose this idea,
    https://oracle-base.com/articles/misc/one-true-lookup-tables-otlt
    I would still use it in the design based for below scenario:
    It is not a domain object and it is purely for display, like Y/N Yes/No, M/F Male/Female, States, Citys, 省份, 城市, 人员类别 etc.  

    Conclusion: store code in the database if the application logic need to reference the code.
    Enum Column
    Create Hibernate AttributeConvert for each Enum

## Performance
- For pagination, when offset is big, limit the pages return or retrieve the id first
## Database migration
Use flyway/liqidbase to create and maintenance database, prefer liqidbase as it support more database.
- Avoid of using columnDefinition as it is db specific
- Use java initial value for default value
- hibernate auto ddl set to validate
- When deploy to k8s, suggest to use init-containers:  
    https://stackoverflow.com/questions/61387510/how-to-solve-liquibase-waiting-for-changelog-lock-problem-in-several-pods-in-ope  
## MySQL
- Set Character Set to 'utf8mb4', Collation to 'utf8mb4-unicode-ci'
- Set timezone to local timezone
- Set case insensible

# React
## Folder structure
Grouping by features or routes, Follow React suggestion: https://reactjs.org/docs/faq-structure.html  
DON'T over-thinking
Cannot use class name for state property, but why???
UI Component library choose ant.design over material UI as it is supported by Alibaba and the reviews are all positive.
Update: ant.design is designed to work with dva (equivalent to react-redux) and umi (equivalent to react-route), so will not use it.
Update: material-ui Keyboard
Use native input
Load configuration table at App to improve system performance???
Table: Use material-table because its functionality is complete and api is simple
Form design
- layout: support one or two columns
- form item: support label and input horizontal or vertical
- error message: should support horizontal or vertical or display error summary
- server error message should display as message box (JS alert would be enough)
Copy to new CRUD
- Explorer: copy folder, and rename files
- VSCode: in search, specify files to include "./src/transaction", click "match case", replace account/Account/ACCOUNT
- Modify xxxList/xxxForm
- Modify index.js/App.js

## Pagination
- Client-side pagination:  
    POSB transaction use this approach. System has a pre-defined criteria when the transaction page is loaded, allow user to modify the query parameter in page.
- Server-side pagination:  
    what is the user case???

# API
## API naming
If the return is an array, use plural nouns
Datetime format follow ISO 8601 "2012-04-23T18:25:43.511Z"

# Java
## Datetime
Datetime type mapping to Java 8 java.time.*, reasons below:
- new version
- Capable of handling of UTC time
- no other annotation like Tempory(...) is needed for hibernate
## Soft Deletion
- Use @SqlDelete and @PreRemove
- To filter out isDeleted rows, use @Where for every Entity. There are few other approaches:
- Hibernate Filter: Although we can enable hibernate filter globally, but it is not working for FindById, this is a security flaw.
https://stackoverflow.com/questions/45169783/hibernate-filter-is-not-applied-for-findone-crud-operation
- Create custom JpaRepositoty and define the query for each interface
- @Where is recommended by most articles for multi-tenancy
Audit log
Use @EntityListener to set audit columns values
Exception Handling
Use @ControllerAdvice to enable global exception handing
For business logic error which we want to show user the detailed error message
For other exceptions, we want to show generic error message
## Muli-tenant
- Define a TenantContext with ThreadLocal fields to store tenant information
- Set tenant information to TenantContext in HttpRequest filter
- Apply hibernate filter (e.g.MuliTenantFilter) to TenantAware entities
- Use AOP to enable the filter
A way to ease this, would be some kind of a listener that is called whenever a new Hibernate Session is created (AOP) and allows me to enable the Filter based on my criteria at that point. An important point is that I need to be able to pass parameters to the filter

## Datetime issue
In Html, when JSON serialize the object to generate request body:-
- date input html element contains value as 'YYYY-MM-DD'
- date variable will be convert to ISO 8061 format 'YYYY-MM-DDTHH:MM:SSZ'
For Java, the jackson parser will fail to parse if the target type is Date when
java.util.Date/Datetime also don't have UTC information, but java.time.OffsetDateTime has information.
in MySql, date value can be stored in DATE/DATETIME or TIMESTAMP. TIMESTAMP is a 4 byte, so it can only present date from 19700101UTC (unix epoch, has the information of UTC) to 20381231 (due to integer limitation).
TimeZone
MySql default timezone is +00:00 for windows; SYSTEM for ubuntu.
JVM timezone is from OS.
Browser timezone is from OS
Conclusion,
For Date type:-
- Html: use html input date for date value 'YYYY-MM-DD'
- Spring: use LocalDate as the value don't have any UTC information
- MySql: use Date type for user enter date. use timestamp for audit column.
For DateTime type:-
- Html: use html input datetime for datetime value "2020-05-23T13:00"
- Spring: use LocalDateTime, the value is same as passed in, but MySql will store UTC time. (Cannot use OffsetDateTime as it will cause exception when parse and binding)
- MySql: use DateTime type for user enter datetime. use timestamp for audit column.

# Docker
## Memory  
https://dzone.com/articles/how-to-decrease-jvm-memory-consumption-in-docker-u
https://stackoverflow.com/questions/44491257/how-to-reduce-spring-boot-memory-usage
https://stackoverflow.com/questions/57271442/how-can-i-decrease-spring-boot-application-memory-usage-in-docker?noredirect=1&lq=1

# Microservice
## Log
https://www.youtube.com/watch?v=5s9pR9UUtAU

# Seckill
Spring + Spring MVC + MyBatis project with high concurrency for products kill

# Project Source
From imooc, an open programming learning platform in China

# Environment
- Java: JDK 1.8
- database: MySql 8.0
- web container: Tomcat
- IDE: IDEA
- dependency management tool: Maven (best tool to influde jars in java projects!)

# Related skills
## MySql

- Scheme design
- Procedure and transaction
## Spring

- Spring IoC
- use of declarative transactions
## MyBatis

- Implementing DAO layer, designing java entities, mapping sql statements to Java methods.
- Integrating into Spring framework
## Spring MVC

- Implementing Web layer
- Designing Controllers
- Developing RESTful API
## Concurrency optimization

- Redis as cache
- MySql procedure
## Front end

- Bootstrap
- JQuery

# Process
## DAO layer
This layer deals with database and data entities.

- Creating database and tables
- Creating java entities accordingly
- Implementing Dao and Mapper interfaces (xxxDao.java and xxxDao.xml) in MyBatis way
- Integrating Spring and MyBatis
- Unit testing
## Service layer
This layer deals with web services.

- Implementing DTO classes which is used to encapsulate data transfered between Web layer and Service Layer (classes under "dto" directory).
- Designing and implementing interfaces (under service directory)
- Confifuring with Spring such as declarative transactiond (spring-service.xml) 
- Unit testing
## Web layer

- Implementing controllers and routes.
- RESTful guidelines
- Designing front end i.e. web pages (.jsp)
- Unit testing
## Concurrency optimization
### Analysis
There are several reasons that restrict the concurrency.
1. Row lock. When multiple transaction threads access ont row, one threading writing data will cause MySql row lock, blocking all other thread. So we need to remove time-consuming I/O out of transaction.
2. When a huge number of requests coming in in a short period of time for a static resources, it will cause unneccessary load for the server. So the idea is to put static resources(js, css..) and web pages into CDN, reducing server load.
3. Constantly fetching hot data from Mysql is a waste of time. Use Redis as the cache to store hot data.

### Implementation

- Configuring Redis (setting up Redis server) and integrating it into the project (jars, adding Dao, modifying services methods)
- In database interactions (insert and update), remove client logic to MySql server, i.e. instead writing condition statements in java, encapsulating all operations in a MySql procedure, thus avoiding networking delay and affects of GC 
- CDN is not used in this personal project. But it's useful for company websites, by storing static resources and web pages in CDN servers.

### Other ways to enhance concurrency
- deploying server clusters
- using Nginx for load balancing
- database sharding (splitting tables)
- Hadoop

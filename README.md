# My Catalog

[![My-Catalog CI](https://github.com/GabrielPivoto/my-catalog/actions/workflows/build.yml/badge.svg)](https://github.com/GabrielPivoto/my-catalog/actions/workflows/build.yml)

[![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)](https://www.jetbrains.com/idea/)
[![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/br/java/technologies/downloads/)

An application to register movies and tv shows watched. 🎬🍿

<h4 align="left"> 
	Author ✏️: <a href="https://github.com/GabrielPivoto">Gabriel Pivoto</a>
</h4>

#### If you want, you can download the files:

[![Dowload](https://custom-icon-badges.demolab.com/badge/-Presentation-F25237?style=for-the-badge&logo=download&logoColor=white)](https://github.com/GabrielPivoto/my-catalog/raw/master/presentation/My%20Catalog.pdf)
[![Dowload](https://custom-icon-badges.demolab.com/badge/-Model-F25237?style=for-the-badge&logo=download&logoColor=white)](https://github.com/GabrielPivoto/my-catalog/raw/master/modeling/model.png)

This application consumes the [OMDB API](https://www.omdbapi.com/).

### What is needed 🧾
- [x] [JDK](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
- [x] [Maven](https://maven.apache.org/download.cgi)
- [x] [Docker](https://www.docker.com/)
- [x] [Postman](https://www.postman.com/)

---
### Starting ▶️

- Clone the repository:

```
https://github.com/GabrielPivoto/my-catalog.git
```
- Create the network:
```
docker network create inatel
```

- Start the MySql container:

```
docker container run --name mysql --network=inatel -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=my_catalog_db -p 3306:3306 -p 33060:33060 -d mysql
```

Now run the project in your IDE, and you're good to go.

### Starting with the Docker Compose 🐋

- Clone the repository 
- At the root of the project, run the command:
```
mvn clean install -DskipTests
```
- Also at the root of the project, build the application image:
```
docker build -t my-catalog .
```
- Run the Docker Compose file:
```
docker compose up
```
### How it works ⚙️

In this application, the user can register all movies and series he have already watched. 
It is possible to register a new movie/series; search movies and series that are registered;
 update movie/series; delete movie/series.

🎥 To add a new movie/series:

- POST ``http://localhost:8080/show`` - requires the following body:

```
{
    "title": "showTitle",
    "personalScore": score
}
```
🎥 To get all movies/series registered:

- GET ``http://localhost:8080/show``
- GET ``http://localhost:8080/show/id`` - gets a show by specific id
- GET ``http://localhost:8080/show?type=movie`` - gets all movies registered
- GET ``http://localhost:8080/show?type=series`` - gets all series registered

🎥 To update a movie/series:

- PATCH ``http://localhost:8080/show`` - requires the following body and updates only the personal score.
```
{
    "title": "showTitle",
    "personalScore": score
}
```
🎥 To remove a movie/series:

- DELETE ``http://localhost:8080/show/id`` - deletes a show by specific id

### Tests 🧪

There are two test classes in the project: ShowControllerTest and ShowServiceTest
. You can either run them from your IDE or run the following command
at the root of the project:

```
mvn test
```

There's also two feature files regarding the methods GET and POST.
To run these tests you just need the CucumberRunnerTests class. A
link to the tests report will be automatically provided.
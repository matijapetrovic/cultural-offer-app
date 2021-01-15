
# Full stack application built with Spring Boot, Hibernate, PostgreSQL, Angular, Google Maps, Docker etc

## How to run

### 1. Database (Optional)

* If you don't want to run the docker image and want to configure the database yourself, edit the application.properties file located in

```
culture-api/application/src/main/resources/application.properties
```

* Download [Docker](https://docs.docker.com/docker-for-windows/install/) (Must enable [Hyper-V](https://docs.microsoft.com/en-us/virtualization/hyper-v-on-windows/quick-start/enable-hyper-v) feature on Windows);

* To verify everything is working correctly:

```bash
docker -v
```

```bash
docker-compose -v
```

* Start the database

```bash
docker-compose up
```

### 2. Start Spring boot app

* Change working directory to the api directory

```bash
cd culture-api
```

* Import the maven project into your favourite IDE or build the jar with maven and start manually

### 3. Angular CLI

* Install the CLI using npm

```bash
npm install -g @angular/cli
```

### 4. Start Angular app

* Position yourself at the project root

* Change working directory to where the web app lives

```bash
cd culture-ui
```

* Install the required packages

```bash
npm install
```

* Finally start the web app

```bash
ng serve
```

## Authors

* Matija Petrović [GitHub](https://github.com/matijapetrovic/)
* Nikola Kabašaj [GitHub](https://github.com/nikolakabasaj/)
* Jovan Bodroža [GitHub](https://github.com/roza44/)
* Nemanja Jevtić [GitHub](https://github.com/njevtic22)
# JavaFX-SpringBoot-Samples

Projects

IgniteDemo - JavaFX & SpringBoot with two views.

SpringBootRest - Rest Server using JPA, Hibernate, H2 in-memory database
		Provides CRUD REST endpoints for MusicCategory table
		Synchronous traditional REST server, uses embedded Tomcat.
		See applications.properties for custom configuration.

SpringBootRestMySql - Rest Server using JPA, Hibernate, MySQL database. You must
		install MySQL (Community Edition is fine)
		See the application.properties file for custom configuration, 
		including root password

		Optionally install MySQLWorkbench to initialize database 
		with schema and data

SpringBootWebClientFX - JavaFX & SpringBoot client that provides CRUD operations.
		Uses WebFlux WebClient in blocking mode. 
		You must run the SpringBootRest (or SpringBootRestMySql) server first.

SpringBootWebClientFXMultiThread - JavaFX & SpringBoot client that provides CRUD
		operations.
		Uses WebFlux WebClient in blocking mode
		Uses JavaFX Concurency library to make REST calls in a
		background thread
		Includes a delay so you can see the ProgressIndicator.
		You must run the SpringBootRest (or SpringBootRestMySql) server first.

WeatherService - SpringBoot Reactive WebService
		Emits random Weather forecasts on interval as server sent events
		Uses SpringBoot WebFlux library

WeatherJFXClient - JavaFX & SpringBoot client that subscribes to the Reactive 
		Weather Service and displays weather forecasts as they arrive.
		You must run WeatherService first.


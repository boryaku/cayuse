# cayuse
Spring Boot GraphQL - Cayuse Demo

The project is a Spring Boot implementation of https://www.graphql-java.com/ project,
which is a GraphQL server implementation for Java.

To Run the app 

```
> ./gradlew bootRun
```

Explore the API

Open 
```
http://localhost:8080/graphiql/
```

Run a query to verify the API is up and running
```graphql
{
	city(zipCode: "90210"){
		id
		zipCode
		name
		temp
		timeZone
		elevation
		lat
		lon
	}
}
```
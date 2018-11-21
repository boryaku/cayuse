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

Run a query to verify the API is up and running, paste the following query into the left hand pane and press the play 
button at the top left. You can change the zipCode value in the query to get the results for that city.
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
Note: The way GraphQL works is if you don't include timeZone or elevation in your query then the back-end
won't make the API call, you can play with the attributes you want returned.

To Run the tests 

```
> ./gradlew test
```
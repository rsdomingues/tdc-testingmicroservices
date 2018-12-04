# TDC Sample Search

### Code template

We are using [Google Java Style](https://google.github.io/styleguide/javaguide.html)
- [*IntelliJ plugin*](https://plugins.jetbrains.com/plugin/8527-google-java-format)
- [Source code](https://github.com/google/google-java-format)

### IntelliJ plugin

- [*IntelliJ plugin*](https://plugins.jetbrains.com/plugin/8527-google-java-format)
- [Lombok](https://plugins.jetbrains.com/plugin/6317-lombok-plugin)
- [Spock Framework](https://plugins.jetbrains.com/plugin/7114-spock-framework-enhancements)
- [Gherkin](https://plugins.jetbrains.com/plugin/7211-gherkin)
- [Cucumber](https://plugins.jetbrains.com/plugin/7212-cucumber-for-java)


### Start application with spring cloud config
```
mvn spring-boot:run
```

### Run Gatling test
```
cd component-test
mvn clean package -DskipTests gatling:execute 
```

### Import data for DynamoDB

 - Setup and configure [AWS CLI](http://docs.aws.amazon.com/cli/latest/userguide/installing.html)

``` 
 - Use the following command in the corresponding directory of the JSON files
     $ aws dynamodb batch-write-item --request-items file://YourArchiveName.json
 - JSONs directory //dependencies/DynamoDB/
 - How to write more than 25 items/rows into DynamoDB table? 
   AWS has a limitation for data import, accepting only 25 put Requests per file. So you will need to create a script to split the data for writing.
 
   Example to import x files for table:
 
     $ for i in $(seq -w 1 1 42); do aws dynamodb batch-write-item --request-items file://salple$i.json; sleep 10; done
```

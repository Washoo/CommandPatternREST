# Desing Patterns

This proyect is to implement somes desing patterns on Spring Boot Framework.

## Starting.. 🚀

We'll download these tools to compile the proyect in our local machine:

### Tools
* [Maven](https://maven.apache.org/) - Dependency Management
* [OpenJDK 8](https://openjdk.java.net/install/) - Development Kit
* [Spring Boot](https://spring.io/projects/spring-boot) - Framework

### Problem

We want to create different methods to obtain or save data of heros. With Spring you can build REST services too fast and we'll use our desing patterns to show the magic for implement them on the methods.

### Command pattern

Using different types of hero commands For example, add new hero or get all heros from the list. Implement ICommand by types of request and response objects we can access to the command easily. But the problem with these implementation is create one dependency for each command in the controller.

![Screenshot](CommandDesign.png)

![Screenshot](command1.png)

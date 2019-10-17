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

![Screenshot](CommandDesign.png)


Now, the next implementation of ICommand using generic types to refer a specify command.

```
public interface ICommand<T, S> {
			
	public S execute(T request);	
}
```

Our first command is to get all the heros on the list on HeroList.
```
public class HeroList {

	private static List<Hero> heros;
	
	static {
		heros = new ArrayList<Hero>(
				Arrays.asList(
						new Hero("Clark", "Kent", 20, "Superman"),
						new Hero("Bruce", "Wayne", 30, "Batman")));
	}
	
	public static List<Hero> getHeros() {
		return heros;
	}
	
	public static void sethero(Hero hero) {
		heros.add(hero);
	}
}
```

Only with:
```
command.execute();
```

JSON response is:
```
GET: localhost:8084/hero/all
{
    "body": {
        "heros": [
            {
                "name": "Clark",
                "lastName": "Kent",
                "age": 20,
                "alias": "Superman"
            },
            {
                "name": "Bruce",
                "lastName": "Wayne",
                "age": 30,
                "alias": "Batman"
            }
        ]
    }
}
```
That's good!

When we have 3 hero operation the controller look like:
```
@RestController
@RequestMapping("/hero")
public class Controller {
	
	@Autowired
	private ICommand<Response<HeroNames>, Request<?>> command1;
	
	@Autowired
	private ICommand<Response<AllHeros>, Request<?>> command2;
	
	@Autowired
	private ICommand<Response<?>, Request<Hero>> command3;
}
```

Using different types of hero commands For example, add new hero or get all heros from the list. Implement ICommand by types of request and response objects we can access to the command easily. But the problem with these implementation is create one dependency for each command in the controller.

![Screenshot](command1.png)

To improve the implementation of the interface *ICommand* we'll use another pattern in the proyect.

### Factory command
I created a enum to save the names of the command.
```
public enum CommandEnum {

	ALL_HEROS,
	ADD_HERO,
	GET_NAMES
}
```
The class to register the commands and the interfaces on a map.
```
@Component("Registry")
public class Registry {

	private static Map<CommandEnum, ICommand> mapCommand = new EnumMap<>(CommandEnum.class);
	
	private static Registry registry;
	
	Registry() {
		setRegistry(this);
	}
	
	public static Registry getRegistry() {
		return registry;
	}
	
	public static void setRegistry(Registry registry) {
		Registry.registry = registry;
	}
		
	public void register(ICommand command, CommandEnum cenum) {
		mapCommand.put(cenum, command);
	}
	
	public ICommand getCommand(CommandEnum cenum) {
		return mapCommand.get(cenum);
	}
}
```

And register the command with the constructor when the bean initialize.
```
public HeroNamesCommand() {
    Registry.getRegistry().register(this, CommandEnum.GET_NAMES);
}
```

Now in the controller we can call the command using the registry to ger the context of the commands.
```
command.execute(CommandEnum.ALL_HEROS);
```

### Decorator command

We can use a decorator to wrap our command and add extra features before and after execute them.
The decorator must implement the `ICommand` to get the context of the command to execute it.
```
public abstract class LoggerDecorator<T, S> implements ICommand<T, S>{
	
	protected final ICommand<T, S> command;
	
	public LoggerDecorator(ICommand<T, S> command) {
		this.command = command;
	}
	
	@Override
	public S execute(T request) {
		return command.execute(request);
	}
}
```
The next class extends of LoggerDecorator to add our features before and after the execution of the command.
```
public class LoggerDecoratorTrace<T, S> extends LoggerDecorator<T, S> {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	public LoggerDecoratorTrace(ICommand<T, S> command) {
		super(command);
	}
	
	@Override
	public S execute(T request) {
		
		logger(request, "Request: ");
		
		S response = command.execute(request);
		
		logger(response, "Response: ");
		
		return response;
	}
	
	private <D> void logger(D object, String type) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			this.logger(type + mapper.writeValueAsString(object));
			
		} catch (JsonProcessingException e) {
			LOGGER.error("The object cannot be convert to string value.");
		}
	}
	
	private void logger(String value) {
		LOGGER.info(value);
	}
}
```

In this case, I'm going to log the request and response of the commands before and after execute it.
```
@SuppressWarnings("unchecked")
@PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Response<?>> addHero(
        @RequestBody Request<Hero> request) {
    
    LoggerDecorator<Request<Hero>, Response<?>> decorator = new LoggerDecoratorTrace<>(registry.getCommand(CommandEnum.ADD_HERO));
    
    return new ResponseEntity<Response<?>>(decorator.execute(request), HttpStatus.OK);
}
```

The LoggerDecoratorTrace wrap the command type, execute the command and log the request before and the response after the command.

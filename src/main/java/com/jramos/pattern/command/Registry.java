package com.jramos.pattern.command;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.jramos.pattern.interfaces.ICommand;

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

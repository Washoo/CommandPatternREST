package com.jramos.pattern.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jramos.pattern.interfaces.ICommand;

/**
 * @author jhoelramos
 * 
 * Class to log all the objects that controller use to execute commands.
 * Extends @LoggerDecorator to implement the interface @ICommand to execute command.
 * Wrap the interface @ICommand to execute before and after the command.
 *
 * @param <T> as Request
 * @param <S> as Response
 */
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

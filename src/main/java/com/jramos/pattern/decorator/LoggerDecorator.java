package com.jramos.pattern.decorator;

import com.jramos.pattern.interfaces.ICommand;

/**
 * @author jhoelramos
 *
 * Implement @ICommand to implement to ovveride the method execute and take wich command execute.
 * @param <T>
 * @param <S>
 */
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

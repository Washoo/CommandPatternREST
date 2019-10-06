package com.jramos.pattern.interfaces;


/**
 *  Interface to implement by commands.
 * @author jhoelramos
 * @param <S>
 *
 */
public interface ICommand<T, S> {
			
	public S execute(T request);	
}

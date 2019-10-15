package com.jramos.pattern.command;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.jramos.pattern.command.dto.AllHeros;
import com.jramos.pattern.dto.HeroList;
import com.jramos.pattern.interfaces.ICommand;
import com.jramos.pattern.utils.Request;
import com.jramos.pattern.utils.Response;

@Service
@DependsOn({"Registry"})
public class AllHerosCommand implements ICommand<Request<?>, Response<AllHeros>>{

	public AllHerosCommand() {
		Registry.getRegistry().register(this, CommandEnum.ALL_HEROS);
	}
	
	@Override
	public Response<AllHeros> execute(Request<?> request) {
		return getHeros();
	}
	
	private Response<AllHeros> getHeros() {
		
		AllHeros allHeros = new AllHeros();
		allHeros.setHeros(HeroList.getHeros());
		return buildResponse(allHeros);
	}
	
	private Response<AllHeros> buildResponse(AllHeros heroList) {
		return new Response<AllHeros>(heroList);
	}
}

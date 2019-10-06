package com.jramos.pattern.command;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.jramos.pattern.dao.HeroList;
import com.jramos.pattern.entity.Hero;
import com.jramos.pattern.interfaces.ICommand;
import com.jramos.pattern.utils.Request;
import com.jramos.pattern.utils.Response;

@Service
@DependsOn({"Registry"})
public class AddHeroCommand implements ICommand<Request<Hero>, Response<?>>{
	
	public AddHeroCommand() {
		Registry.getRegistry().register(this, CommandEnum.ADD_HERO);
	}

	@Override
	public Response<?> execute(Request<Hero> request) {
		return addHero(request);
	}
	
	private Response<?> addHero(Request<Hero> request) {
		
		HeroList.sethero(request.getBody());
		return setResponse();
	}
	
	private Response<?> setResponse() {
		return new Response<Object>();
	}
}

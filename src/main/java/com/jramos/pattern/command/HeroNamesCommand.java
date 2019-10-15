package com.jramos.pattern.command;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.jramos.pattern.command.dto.HeroNames;
import com.jramos.pattern.dto.HeroList;
import com.jramos.pattern.interfaces.ICommand;
import com.jramos.pattern.utils.Request;
import com.jramos.pattern.utils.Response;

/**
 * Command to get all the heros.
 * @author jhoelramos
 *
 * @param <T> is @HeroNames
 */
@Service
@DependsOn({"Registry"})
public class HeroNamesCommand implements ICommand<Request<?>, Response<HeroNames>>{

	public HeroNamesCommand() {
		Registry.getRegistry().register(this, CommandEnum.GET_NAMES);
	}
	
	@Override
	public Response<HeroNames> execute(Request<?> request) {
		return getNames();
	}
	
	public Response<HeroNames> getNames() {
		
		List<String> names = new ArrayList<>();
		
		HeroList.getHeros().forEach(
				(hero) -> names.add(hero.getName()));
		
		return setBody(new HeroNames(names));
	}
	
	private Response<HeroNames> setBody(HeroNames body) {
		return new Response<HeroNames>(body);
	}
}

package com.jramos.pattern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jramos.pattern.command.CommandEnum;
import com.jramos.pattern.command.Registry;
import com.jramos.pattern.command.dto.AllHeros;
import com.jramos.pattern.command.dto.HeroNames;
import com.jramos.pattern.decorator.LoggerDecorator;
import com.jramos.pattern.decorator.LoggerDecoratorTrace;
import com.jramos.pattern.entity.Hero;
import com.jramos.pattern.utils.Request;
import com.jramos.pattern.utils.Response;

/**
 * Controller with hero operations.
 * @author jhoelramos
 *
 */
@RestController
@RequestMapping("/hero")
public class Controller {
	
	@Autowired
	private Registry registry;
	
	/**
	 * Execute the command to get all the names of the heros.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/get-names", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<HeroNames>> getHeroNames() {
		
		LoggerDecorator<Request<?>, Response<HeroNames>> decorator = new LoggerDecoratorTrace<>(registry.getCommand(CommandEnum.GET_NAMES));
		
		return new ResponseEntity<Response<HeroNames>>(decorator.execute(Request.EMPTY), HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<AllHeros>> getHeros() {
		
		LoggerDecorator<Request<?>, Response<AllHeros>> decorator = new LoggerDecoratorTrace<>(registry.getCommand(CommandEnum.ALL_HEROS));
		
		return new ResponseEntity<Response<AllHeros>>(decorator.execute(Request.EMPTY), HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<?>> addHero(
			@RequestBody Request<Hero> request) {
		
		LoggerDecorator<Request<Hero>, Response<?>> decorator = new LoggerDecoratorTrace<>(registry.getCommand(CommandEnum.ADD_HERO));
		
		return new ResponseEntity<Response<?>>(decorator.execute(request), HttpStatus.OK);
	}
}

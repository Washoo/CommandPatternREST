package com.jramos.pattern.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jramos.pattern.entity.Hero;

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
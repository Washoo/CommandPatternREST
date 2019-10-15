package com.jramos.pattern.command.dto;

import java.util.List;

import com.jramos.pattern.model.Hero;

public class AllHeros {

	private List<Hero> heros;

	public List<Hero> getHeros() {
		return heros;
	}

	public void setHeros(List<Hero> heros) {
		this.heros = heros;
	}
}

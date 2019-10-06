package com.jramos.pattern.command.dto;

import java.util.List;

public class HeroNames {

	private List<String> names;

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public HeroNames(List<String> names) {
		this.names = names;
	}
}

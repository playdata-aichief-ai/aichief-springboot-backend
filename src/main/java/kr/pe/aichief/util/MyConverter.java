package kr.pe.aichief.util;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MyConverter {

	public String spaceJoiner(List<String> ls) {
		return String.join(" ", ls);
	}
}

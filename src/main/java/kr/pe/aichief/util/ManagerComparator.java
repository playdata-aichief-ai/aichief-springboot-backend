package kr.pe.aichief.util;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import kr.pe.aichief.model.entity.Manager;

@Component
public class ManagerComparator implements Comparator<Manager> {

	@Override
	public int compare(Manager o1, Manager o2) {
		
		return o1.getAssigns().size() - o2.getAssigns().size();
	}

}

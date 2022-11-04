package kr.pe.aichief.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.pe.aichief.model.entity.Assign;
import kr.pe.aichief.model.entity.Manager;

@Disabled
@SpringBootTest
public class ManagerComparatorTest {
	
	@Autowired
	private ManagerComparator managerComparator;
	
	@Test
	void comparatorTest() {
		List<Assign> assigns1 = new ArrayList<Assign>();
		List<Assign> assigns2 = new ArrayList<Assign>();
		
		assigns1.add(Assign.builder().build());
		assigns1.add(Assign.builder().build());
		
		assigns2.add(Assign.builder().build());
		
		Manager manager1 = Manager.builder().assigns(assigns1).build();
		Manager manager2 = Manager.builder().assigns(assigns2).build();
		
		List<Manager> managers = new ArrayList<Manager>();
		
		managers.add(manager1);
		managers.add(manager2);
		
		managers.forEach(manager -> System.out.println(manager.getAssigns().size()));
		
		Collections.sort(managers, managerComparator);
		
		managers.forEach(manager -> System.out.println(manager.getAssigns().size()));
	}

}

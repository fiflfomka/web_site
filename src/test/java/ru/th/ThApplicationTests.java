package ru.th;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.th.DAO.ManDAO;
import ru.th.models.Man;

import java.util.List;

@SpringBootTest
class ThApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void test_Man() {
		System.out.println("I AM HERE");
		System.out.println("I AM HERE");
		System.out.println("I AM HERE");
		System.out.println("I AM HERE");
		ManDAO man_dao = new ManDAO();
		Man man = man_dao.findById(4);
		System.out.println(man.man_id);
		System.out.println(man.name);
		System.out.println(man.description);
		List<Man> all_man = man_dao.getAll();
		for (Man element : all_man) {
			System.out.println(element.man_id);
			System.out.println(element.name);
			System.out.println(element.description);
		}
	}

}

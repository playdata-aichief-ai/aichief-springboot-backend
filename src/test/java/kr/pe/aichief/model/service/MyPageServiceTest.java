package kr.pe.aichief.model.service;

import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Disabled
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MyPageServiceTest {
	
	// 수익자
	
	// 내 정보 수정
	// 내 계약 조회
	// 내 청구 조회
	
	// 담당자
	
	// 내 정보 조회
	// 내 정보 수정
	// 내 고객 조회
	// 내 청구 조회
}

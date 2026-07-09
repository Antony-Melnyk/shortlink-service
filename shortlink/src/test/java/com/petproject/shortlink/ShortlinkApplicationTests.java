package com.petproject.shortlink;

import com.petproject.shortlink.config.TestCacheConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestCacheConfig.class)
class ShortlinkApplicationTests {

	@Test
	void contextLoads() {
	}
}
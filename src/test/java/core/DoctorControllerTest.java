package core;

import core.controller.DoctorController;
import core.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for Doctor Controller
 * Verifies that the Spring context loads correctly
 */
@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
class DoctorControllerTest {

    @Autowired
    private DoctorController doctorController;

    @Autowired
    private DoctorService doctorService;

    @Test
    void contextLoads() {
        assertThat(doctorController).isNotNull();
        assertThat(doctorService).isNotNull();
    }
}

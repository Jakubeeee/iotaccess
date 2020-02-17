package com.jakubeeee.masterthesis.core;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = CoreApplicationEntryPoint.class
)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class
})
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest")
@TestPropertySource(locations = "classpath:core-integrationtest.properties")
@PropertySource(value = "classpath:generated/testdatabase-generated.properties", ignoreResourceNotFound = true)
public abstract class BaseIntegrationTest {

    private static final boolean AUTOCONFIGURE_TEST_DATABASE = true;

    static {
        if (AUTOCONFIGURE_TEST_DATABASE)
            CustomPostgreSQL12Container.getInstance().start();
    }

}

package com.jakubeeee.iotaccess.core;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import static com.jakubeeee.iotaccess.core.BaseIsolatedIntegrationTest.IsolatedTestExecutionListener;
import static java.util.Objects.requireNonNull;
import static org.springframework.test.annotation.DirtiesContext.HierarchyMode.EXHAUSTIVE;

@TestExecutionListeners({
        IsolatedTestExecutionListener.class
})
public abstract class BaseIsolatedIntegrationTest extends BaseIntegrationTest {

    static class IsolatedTestExecutionListener extends AbstractTestExecutionListener {

        @Override
        public int getOrder() {
            return HIGHEST_PRECEDENCE;
        }

        @Override
        public void beforeTestClass(TestContext testContext) {
            requireNonNull(testContext).markApplicationContextDirty(EXHAUSTIVE);
        }

        @Override
        public void afterTestClass(TestContext testContext) {
            requireNonNull(testContext).markApplicationContextDirty(EXHAUSTIVE);
        }

    }

}

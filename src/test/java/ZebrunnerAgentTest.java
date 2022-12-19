import api.AuthService;
import api.ReportingService;
import api.TestService;
import api.enums.TestStatuses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;

public class ZebrunnerAgentTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test()
    public void testRunStartTest() {
        LOGGER.info("Test Run start");
        AuthService.refreshAuthToken();
        TestService.testRunStart();
        Assert.assertTrue(ReportingService.getInstance().getTestRunResult().equalsIgnoreCase(TestStatuses.IN_PROGRESS
                .getStatusName()), "Actual test status differs from the expected one");
    }

    @Test()
    public void testExecutionStartTest() {
        LOGGER.info("Test Execution start");
        AuthService.refreshAuthToken();
        TestService.testRunStart();
        TestService.testExecutionStart();
        Assert.assertTrue(ReportingService.getInstance().getTestExecutionResult().equalsIgnoreCase(TestStatuses.IN_PROGRESS
                .getStatusName()), "Actual test result differs from the expected one");
    }

    @Test()
    public void testExecutionFinishTest() {
        LOGGER.info("Test Execution finish");
        AuthService.refreshAuthToken();
        TestService.testRunStart();
        TestService.testExecutionStart();
        TestService.testExecutionFinish(TestStatuses.PASSED);
        Assert.assertTrue(ReportingService.getInstance().getTestExecutionResult().equalsIgnoreCase(TestStatuses.PASSED
                .getStatusName()), "Actual test result differs from the expected one");
    }

    @Test
    public void successTest() {
        LOGGER.info("Success test started");
        TestService.startTest(TestStatuses.PASSED);
        String testStatus = ReportingService.getInstance().getStatus();
        LOGGER.info("Success test finished");
        LOGGER.info(testStatus);
        Assert.assertTrue(testStatus.equalsIgnoreCase(TestStatuses.PASSED.getStatusName()),
                "Actual test status differs from the expected one");
    }

    @Test
    public void failTest() {
        LOGGER.info("Failed test started");
        TestService.startTest(TestStatuses.FAILED);
        String testStatus = ReportingService.getInstance().getStatus();
        LOGGER.info("Failed test finished");
        LOGGER.info(testStatus);
        Assert.assertTrue(testStatus.equalsIgnoreCase(TestStatuses.FAILED.getStatusName()),
                "Actual test status differs from the expected one");
    }

    @Test
    public void skippedTest() {
        LOGGER.info("Skipped test started");
        TestService.startTest(TestStatuses.SKIPPED);
        String testStatus = ReportingService.getInstance().getStatus();
        LOGGER.info("Skipped test finished");
        LOGGER.info(testStatus);
        Assert.assertTrue(testStatus.equalsIgnoreCase(TestStatuses.FAILED.getStatusName()),
                "Actual test status differs from the expected one");
    }
}

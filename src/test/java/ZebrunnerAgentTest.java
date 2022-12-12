import api.AuthService;
import api.ConnectionService;
import api.enums.TestStatuses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class ZebrunnerAgentTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    ConnectionService connectionService = new ConnectionService();

    @Test()
    public void testRunStartTest() throws IOException {
        LOGGER.info("Test Run start");
        AuthService.refreshAuthToken();
        connectionService.testRunStart();
        Assert.assertTrue(connectionService.getTestRunResult().equalsIgnoreCase(TestStatuses.IN_PROGRESS.getStatusName()),
                "Actual test status differs from the expected one");
    }

    @Test()
    public void testExecutionStartTest() throws IOException {
        LOGGER.info("Test Execution start");
        AuthService.refreshAuthToken();
        connectionService.testRunStart();
        connectionService.testExecutionStart();
        Assert.assertTrue(connectionService.getTestExecutionResult().equalsIgnoreCase(TestStatuses.IN_PROGRESS.getStatusName()),
                "Actual test result differs from the expected one");
    }

    @Test()
    public void testExecutionFinishTest() throws IOException {
        LOGGER.info("Test Execution finish");
        AuthService.refreshAuthToken();
        connectionService.testRunStart();
        connectionService.testExecutionStart();
        connectionService.testExecutionFinish(TestStatuses.PASSED);
        Assert.assertTrue(connectionService.getTestExecutionResult().equalsIgnoreCase(TestStatuses.PASSED.getStatusName()),
                "Actual test result differs from the expected one");
    }

    @Test
    public void successTest() throws IOException {
        LOGGER.info("Success test started");
        connectionService.startTest(TestStatuses.PASSED);
        String testStatus = connectionService.getStatus();
        LOGGER.info("Success test finished");
        LOGGER.info(testStatus);
        Assert.assertTrue(testStatus.equalsIgnoreCase(TestStatuses.PASSED.getStatusName()), "Actual test status differs from the expected one");
    }

    @Test
    public void failTest() throws IOException {
        LOGGER.info("Failed test started");
        connectionService.startTest(TestStatuses.FAILED);
        String testStatus = connectionService.getStatus();
        LOGGER.info("Failed test finished");
        LOGGER.info(testStatus);
        Assert.assertTrue(testStatus.equalsIgnoreCase(TestStatuses.FAILED.getStatusName()), "Actual test status differs from the expected one");
    }

    @Test
    public void skippedTest() throws IOException {
        LOGGER.info("Skipped test started");
        connectionService.startTest(TestStatuses.SKIPPED);
        String testStatus = connectionService.getStatus();
        LOGGER.info("Skipped test finished");
        LOGGER.info(testStatus);
        Assert.assertTrue(testStatus.equalsIgnoreCase(TestStatuses.FAILED.getStatusName()), "Actual test status differs from the expected one");
    }
}

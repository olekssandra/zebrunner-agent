import api.*;
import api.enums.HTTPStatusCodeType;
import api.enums.TestStatuses;
import api.methods.PostTestExecutionStartMethod;
import api.methods.PostTestRunStartMethod;
import api.methods.PutTestExecutionFinishMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

public class ZebrunnerAgentTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    ConnectionService connectionService = new ConnectionService();

    @Test()
    public void refreshTokenTest() throws IOException {
        LOGGER.info("Authentication start");
        AuthService.refreshAuthToken();
    }

    @Test()
    public void testRunStartTest() {
        LOGGER.info("Test Run start");
        ExecutionService executor = new ExecutionService();
        PostTestRunStartMethod testRunStartMethod = new PostTestRunStartMethod();
        executor.expectStatus(testRunStartMethod, HTTPStatusCodeType.OK);
        String testStatus = ResponseService.readTestStatus(executor.callApiMethod(testRunStartMethod));
        testRunStartMethod.validateResponse();
        Assert.assertTrue(testStatus.equalsIgnoreCase(TestStatuses.IN_PROGRESS.getStatusName()), "Actual test status differs from the expected one");
    }

    @Test()
    public void testExecutionStartTest() {
        LOGGER.info("Test Execution start");
        ExecutionService executor = new ExecutionService();
        PostTestExecutionStartMethod testExecutionStartMethod = new PostTestExecutionStartMethod();
        executor.expectStatus(testExecutionStartMethod, HTTPStatusCodeType.OK);
        String result = ResponseService.readResult(executor.callApiMethod(testExecutionStartMethod));
        testExecutionStartMethod.validateResponse();
        Assert.assertTrue(result.equalsIgnoreCase(TestStatuses.IN_PROGRESS.getStatusName()), "Actual test result differs from the expected one");
    }

    @Test()
    public void testExecutionFinishTest() throws IOException {
        LOGGER.info("Test Execution finish");
        Properties props = new Properties();
        props.put(JsonConstant.TEST_RESULT, TestStatuses.PASSED.getStatusName());
        String path = "src/test/resources/api/test_execution/_put/test_execution.properties";
        FileOutputStream outputStrem = new FileOutputStream(path);
        props.store(outputStrem, null);
        ExecutionService executor = new ExecutionService();
        PutTestExecutionFinishMethod testExecutionFinishMethod = new PutTestExecutionFinishMethod();
        testExecutionFinishMethod.setProperties(props);
        executor.expectStatus(testExecutionFinishMethod, HTTPStatusCodeType.OK);
        String result = ResponseService.readResult(executor.callApiMethod(testExecutionFinishMethod));
        testExecutionFinishMethod.validateResponse();
        Assert.assertTrue(result.equalsIgnoreCase(TestStatuses.PASSED.getStatusName()), "Actual test result differs from the expected one");
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

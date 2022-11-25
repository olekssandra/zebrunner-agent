import api.ConnectionService;
import api.ExecutionService;
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
    public void TestRunStartTest() {
        LOGGER.info("Test Run start");
        ExecutionService executor = new ExecutionService();
        PostTestRunStartMethod testRunStartMethod = new PostTestRunStartMethod();
        executor.expectStatus(testRunStartMethod, HTTPStatusCodeType.OK);
        executor.callApiMethod(testRunStartMethod);
        testRunStartMethod.validateResponse();
    }

    @Test()
    public void TestExecutionStartTest() {
        LOGGER.info("Test Execution start");
        ExecutionService executor = new ExecutionService();
        PostTestExecutionStartMethod testExecutionStartMethod = new PostTestExecutionStartMethod();
        executor.expectStatus(testExecutionStartMethod, HTTPStatusCodeType.OK);
        executor.callApiMethod(testExecutionStartMethod);
        testExecutionStartMethod.validateResponse();
    }

    @Test()
    public void TestExecutionFinishTest() throws IOException {
        LOGGER.info("Test Execution finish");
        Properties props = new Properties();
        props.put("result", TestStatuses.PASSED.getStatusName());
        String path = "src/test/resources/api/test_execution/_put/test_execution.properties";
        FileOutputStream outputStrem = new FileOutputStream(path);
        props.store(outputStrem, null);
        ExecutionService executor = new ExecutionService();
        PutTestExecutionFinishMethod testExecutionFinishMethod = new PutTestExecutionFinishMethod();
        testExecutionFinishMethod.setProperties(props);
        executor.expectStatus(testExecutionFinishMethod, HTTPStatusCodeType.OK);
        executor.callApiMethod(testExecutionFinishMethod);
        testExecutionFinishMethod.validateResponse();
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

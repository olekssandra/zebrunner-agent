package api;

import api.enums.HTTPStatusCodeType;
import api.enums.TestStatuses;
import api.methods.*;
import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

public class ConnectionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private String testRunId;
    private String testId;
    private String status;
    private String testSessionId;

    public ConnectionService() {
    }

    public String getTestRunId() {
        return testRunId;
    }

    public void setTestRunId(String testRunId) {
        this.testRunId = testRunId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void testRunStart() {
        ExecutionService executor = new ExecutionService();
        PostTestRunStartMethod testRunStartMethod = new PostTestRunStartMethod();
        executor.expectStatus(testRunStartMethod, HTTPStatusCodeType.OK);
        testRunId = JsonPath.from(executor.callApiMethod(testRunStartMethod)).get("id").toString();
        LOGGER.info(testRunId);
    }

    public void testExecutionStart() {
        ExecutionService executor = new ExecutionService();
        PostTestExecutionStartMethod testExecutionStartMethod = new PostTestExecutionStartMethod(testRunId);
        executor.expectStatus(testExecutionStartMethod, HTTPStatusCodeType.OK);
        testId = JsonPath.from(executor.callApiMethod(testExecutionStartMethod)).get("id").toString();
        LOGGER.info(testId);
    }

    public void testExecutionFinish(TestStatuses status) throws IOException {
        Properties properties = new Properties();
        properties.put("result", status.getStatusName());
        String path = "src/test/resources/api/test_execution/_put/test_execution.properties";
        FileOutputStream outputStrem = new FileOutputStream(path);
        properties.store(outputStrem, null);
        ExecutionService executor = new ExecutionService();
        PutTestExecutionFinishMethod testExecutionFinishMethod = new PutTestExecutionFinishMethod(testRunId, testId);
        testExecutionFinishMethod.setProperties(properties);
        executor.expectStatus(testExecutionFinishMethod, HTTPStatusCodeType.OK);
        executor.callApiMethod(testExecutionFinishMethod);
    }

    public void testRunExecutionFinish() {
        ExecutionService executor = new ExecutionService();
        PutTestRunExecutionFinishMethod testRunExecutionFinishMethod = new PutTestRunExecutionFinishMethod(testRunId);
        executor.expectStatus(testRunExecutionFinishMethod, HTTPStatusCodeType.OK);
        status = JsonPath.from(executor.callApiMethod(testRunExecutionFinishMethod)).get("status").toString();
    }

    public void sendTestExecutionLogs() throws IOException {
        Properties properties = new Properties();
        properties.put("testId", testId);
        String path = "src/test/resources/api/test_execution/post_logs/logs.properties";
        FileOutputStream outputStrem = new FileOutputStream(path);
        properties.store(outputStrem, null);
        ExecutionService executor = new ExecutionService();
        PostTestExecutionLogsMethod testExecutionLogsMethod = new PostTestExecutionLogsMethod(testRunId);
        testExecutionLogsMethod.setProperties(properties);
        executor.expectStatus(testExecutionLogsMethod, HTTPStatusCodeType.ACCEPTED);
        executor.callApiMethod(testExecutionLogsMethod);
    }

    public void testSessionComplete() throws IOException {
        Properties properties = new Properties();
        properties.put("testIds", testId);
        String path = "src/test/resources/api/test_session/test_session.properties";
        FileOutputStream outputStrem = new FileOutputStream(path);
        properties.store(outputStrem, null);
        ExecutionService executor = new ExecutionService();
        PostTestSessionCompleteMethod testSessionCompleteMethod = new PostTestSessionCompleteMethod(testRunId);
        testSessionCompleteMethod.setProperties(properties);
        executor.expectStatus(testSessionCompleteMethod, HTTPStatusCodeType.OK);
        testSessionId = JsonPath.from(executor.callApiMethod(testSessionCompleteMethod)).get("id").toString();
    }

    public void testSessionFinishMethod() throws IOException {
        Properties properties = new Properties();
        properties.put("testIds", testId);
        String path = "src/test/resources/api/test_session/test_session.properties";
        FileOutputStream outputStrem = new FileOutputStream(path);
        properties.store(outputStrem, null);
        ExecutionService executor = new ExecutionService();
        PutTestSessionFinishMethod testSessionFinishMethod = new PutTestSessionFinishMethod(testRunId, testSessionId);
        testSessionFinishMethod.setProperties(properties);
        executor.expectStatus(testSessionFinishMethod, HTTPStatusCodeType.OK);
        executor.callApiMethod(testSessionFinishMethod);
    }

    public void startTest(TestStatuses status) throws IOException {
        testRunStart();
        testExecutionStart();
        sendTestExecutionLogs();
        testExecutionFinish(status);
        testRunExecutionFinish();
        testSessionComplete();
        testSessionFinishMethod();
    }

    public void startSkippedTest() {
        testRunStart();
        testExecutionStart();
        testRunExecutionFinish();
    }
}

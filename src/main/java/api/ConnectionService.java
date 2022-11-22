package api;

import api.enums.HTTPStatusCodeType;
import api.methods.*;
import io.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class ConnectionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private String testRunId;
    private String testId;

    private String status;

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

    public void testExecutionFinish() {
        ExecutionService executor = new ExecutionService();
        PutTestExecutionFinishMethod testExecutionFinishMethod = new PutTestExecutionFinishMethod(testRunId, testId);
        executor.expectStatus(testExecutionFinishMethod, HTTPStatusCodeType.OK);
        executor.callApiMethod(testExecutionFinishMethod);
    }

    public void testRunExecutionFinish() {
        ExecutionService executor = new ExecutionService();
        PutTestRunExecutionFinishMethod testRunExecutionFinishMethod = new PutTestRunExecutionFinishMethod(testRunId);
        executor.expectStatus(testRunExecutionFinishMethod, HTTPStatusCodeType.OK);
        status = JsonPath.from(executor.callApiMethod(testRunExecutionFinishMethod)).get("status").toString();
    }

    public void sendTestExecutionLogs() {
        ExecutionService executor = new ExecutionService();
        PostTestExecutionLogsMethod testExecutionLogsMethod = new PostTestExecutionLogsMethod(testRunId);
        executor.expectStatus(testExecutionLogsMethod, HTTPStatusCodeType.ACCEPTED);
        executor.callApiMethod(testExecutionLogsMethod);
    }

    public void startTest() {
        testRunStart();
        testExecutionStart();
        testExecutionFinish();
        testRunExecutionFinish();
    }

    public void startSkippedTest() {
        testRunStart();
        testExecutionStart();
        testRunExecutionFinish();
    }
}

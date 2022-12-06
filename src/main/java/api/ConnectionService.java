package api;

import api.enums.HTTPStatusCodeType;
import api.enums.TestStatuses;
import api.methods.*;
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

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTestSessionId() {
        return testSessionId;
    }

    public void setTestSessionId(String testSessionId) {
        this.testSessionId = testSessionId;
    }

    public void refreshToken() {
        ExecutionService executor = new ExecutionService();
        PostTestRunStartMethod testRunStartMethod = new PostTestRunStartMethod();
        executor.expectStatus(testRunStartMethod, HTTPStatusCodeType.OK);
        testRunId = ResponseService.readId(executor.callApiMethod(testRunStartMethod));
        LOGGER.info(testRunId);
    }

    public void testRunStart() {
        ExecutionService executor = new ExecutionService();
        PostTestRunStartMethod testRunStartMethod = new PostTestRunStartMethod();
        executor.expectStatus(testRunStartMethod, HTTPStatusCodeType.OK);
        testRunId = ResponseService.readId(executor.callApiMethod(testRunStartMethod));
        LOGGER.info(testRunId);
    }

    public void testExecutionStart() {
        ExecutionService executor = new ExecutionService();
        PostTestExecutionStartMethod testExecutionStartMethod = new PostTestExecutionStartMethod(testRunId);
        executor.expectStatus(testExecutionStartMethod, HTTPStatusCodeType.OK);
        testId = ResponseService.readId(executor.callApiMethod(testExecutionStartMethod));
        LOGGER.info(testId);
    }

    public void testExecutionFinish(TestStatuses status) throws IOException {
        Properties properties = new Properties();
        properties.put(JsonConstant.TEST_RESULT, status.getStatusName());
        String path = "src/test/resources/api/test_execution/_put/test_execution.properties";
        FileOutputStream outputStream = new FileOutputStream(path);
        properties.store(outputStream, null);
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
        status = ResponseService.readTestStatus(executor.callApiMethod(testRunExecutionFinishMethod));
    }

    public void sendTestExecutionLogs() throws IOException {
        Properties properties = new Properties();
        properties.put(JsonConstant.TEST_ID, testId);
        String path = "src/test/resources/api/test_execution/post_logs/logs.properties";
        FileOutputStream outputStream = new FileOutputStream(path);
        properties.store(outputStream, null);
        ExecutionService executor = new ExecutionService();
        PostTestExecutionLogsMethod testExecutionLogsMethod = new PostTestExecutionLogsMethod(testRunId);
        testExecutionLogsMethod.setProperties(properties);
        executor.expectStatus(testExecutionLogsMethod, HTTPStatusCodeType.ACCEPTED);
        executor.callApiMethod(testExecutionLogsMethod);
    }

    public void testSessionComplete() throws IOException {
        Properties properties = new Properties();
        properties.put(JsonConstant.TEST_IDS, testId);
        String path = "src/test/resources/api/test_session/test_session.properties";
        FileOutputStream outputStream = new FileOutputStream(path);
        properties.store(outputStream, null);
        ExecutionService executor = new ExecutionService();
        PostTestSessionCompleteMethod testSessionCompleteMethod = new PostTestSessionCompleteMethod(testRunId);
        testSessionCompleteMethod.setProperties(properties);
        executor.expectStatus(testSessionCompleteMethod, HTTPStatusCodeType.OK);
        testSessionId = ResponseService.readId(executor.callApiMethod(testSessionCompleteMethod));
    }

    public void testSessionFinishMethod() throws IOException {
        Properties properties = new Properties();
        properties.put(JsonConstant.TEST_IDS, testId);
        String path = "src/test/resources/api/test_session/test_session.properties";
        FileOutputStream outputStream = new FileOutputStream(path);
        properties.store(outputStream, null);
        ExecutionService executor = new ExecutionService();
        PutTestSessionFinishMethod testSessionFinishMethod = new PutTestSessionFinishMethod(testRunId, testSessionId);
        testSessionFinishMethod.setProperties(properties);
        executor.expectStatus(testSessionFinishMethod, HTTPStatusCodeType.OK);
        executor.callApiMethod(testSessionFinishMethod);
    }

    public void attachTestExecutionLabels() {
        ExecutionService executor = new ExecutionService();
        PutTestExecutionLabelsMethod testExecutionLabelsMethod = new PutTestExecutionLabelsMethod(testRunId, testId);
        executor.expectStatus(testExecutionLabelsMethod, HTTPStatusCodeType.OK_NO_CONTENT);
        executor.callApiMethod(testExecutionLabelsMethod);
    }

    public void startTest(TestStatuses status) throws IOException {
        testRunStart();
        testExecutionStart();
        sendTestExecutionLogs();
        attachTestExecutionLabels();
        testExecutionFinish(status);
        testSessionComplete();
        testRunExecutionFinish();
        testSessionFinishMethod();
    }
}

package api;

import api.enums.HTTPStatusCodeType;
import api.enums.TestStatuses;
import api.methods.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class TestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void testRunStart() {
        ExecutionService executor = new ExecutionService();
        PostTestRunStartMethod testRunStartMethod = new PostTestRunStartMethod();
        executor.expectStatus(testRunStartMethod, HTTPStatusCodeType.OK);
        String rs = executor.callApiMethod(testRunStartMethod);
        ReportingService.getInstance().setTestRunId(ResponseService.readId(rs));
        ReportingService.getInstance().setTestRunResult(ResponseService.readTestStatus(rs));
        testRunStartMethod.validateResponse();
    }

    public static void testExecutionStart() {
        ExecutionService executor = new ExecutionService();
        PostTestExecutionStartMethod testExecutionStartMethod =
                new PostTestExecutionStartMethod(ReportingService.getInstance().getTestRunId());
        executor.expectStatus(testExecutionStartMethod, HTTPStatusCodeType.OK);
        String rs = executor.callApiMethod(testExecutionStartMethod);
        ReportingService.getInstance().setTestId(ResponseService.readId(rs));
        ReportingService.getInstance().setTestExecutionResult(ResponseService.readResult(rs));
        testExecutionStartMethod.validateResponse();
    }

    public static void testExecutionFinish(TestStatuses testStatus) {
        ExecutionService executor = new ExecutionService();
        PutTestExecutionFinishMethod testExecutionFinishMethod = new PutTestExecutionFinishMethod(ReportingService
                .getInstance().getTestRunId(), ReportingService.getInstance().getTestId(), testStatus.getStatusName());
        executor.expectStatus(testExecutionFinishMethod, HTTPStatusCodeType.OK);
        ReportingService.getInstance().setTestExecutionResult(ResponseService.readResult(executor.callApiMethod(testExecutionFinishMethod)));
        testExecutionFinishMethod.validateResponse();
    }

    public static void testRunExecutionFinish() {
        ExecutionService executor = new ExecutionService();
        PutTestRunExecutionFinishMethod testRunExecutionFinishMethod =
                new PutTestRunExecutionFinishMethod(ReportingService.getInstance().getTestRunId());
        executor.expectStatus(testRunExecutionFinishMethod, HTTPStatusCodeType.OK);
        ReportingService.getInstance().setStatus(ResponseService.readTestStatus(executor.callApiMethod(testRunExecutionFinishMethod)));
        testRunExecutionFinishMethod.validateResponse();
    }

    public static void sendTestExecutionLogs() {
        ExecutionService executor = new ExecutionService();
        PostTestExecutionLogsMethod testExecutionLogsMethod =
                new PostTestExecutionLogsMethod(ReportingService.getInstance().getTestRunId());
        executor.expectStatus(testExecutionLogsMethod, HTTPStatusCodeType.ACCEPTED);
        executor.callApiMethod(testExecutionLogsMethod);
    }

    public static void testSessionComplete() {
        ExecutionService executor = new ExecutionService();
        PostTestSessionCompleteMethod testSessionCompleteMethod =
                new PostTestSessionCompleteMethod(ReportingService.getInstance().getTestRunId());
        executor.expectStatus(testSessionCompleteMethod, HTTPStatusCodeType.OK);
        ReportingService.getInstance().setTestSessionId(ResponseService.readId(executor.callApiMethod(testSessionCompleteMethod)));
        testSessionCompleteMethod.validateResponse();
    }

    public static void testSessionFinishMethod() {
        ExecutionService executor = new ExecutionService();
        PutTestSessionFinishMethod testSessionFinishMethod = new PutTestSessionFinishMethod(ReportingService
                .getInstance().getTestRunId(), ReportingService.getInstance().getTestSessionId());
        executor.expectStatus(testSessionFinishMethod, HTTPStatusCodeType.OK);
        executor.callApiMethod(testSessionFinishMethod);
        testSessionFinishMethod.validateResponse();
    }

    public static void attachTestExecutionLabels() {
        ExecutionService executor = new ExecutionService();
        PutTestExecutionLabelsMethod testExecutionLabelsMethod = new PutTestExecutionLabelsMethod(ReportingService
                .getInstance().getTestRunId(), ReportingService.getInstance().getTestId());
        executor.expectStatus(testExecutionLabelsMethod, HTTPStatusCodeType.OK_NO_CONTENT);
        executor.callApiMethod(testExecutionLabelsMethod);
    }

    public static void startTest(TestStatuses testStatus) throws IOException {
        AuthService.refreshAuthToken();
        testRunStart();
        testExecutionStart();
        sendTestExecutionLogs();
        attachTestExecutionLabels();
        testExecutionFinish(testStatus);
        testSessionComplete();
        testRunExecutionFinish();
        testSessionFinishMethod();
    }
}

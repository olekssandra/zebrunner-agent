package api;

import api.enums.HTTPStatusCodeType;
import api.methods.PostAuthenticationMethod;
import api.methods.PostTestExecutionStartMethod;
import api.methods.PostTestRunStartMethod;

import java.io.IOException;

public class AuthService {

    private static String authToken;

    public static String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        AuthService.authToken = authToken;
    }

    public static void refreshAuthToken() throws IOException {
        ExecutionService executor = new ExecutionService();
        PostAuthenticationMethod authenticationMethod = new PostAuthenticationMethod();
        executor.expectStatus(authenticationMethod, HTTPStatusCodeType.OK);
        authToken = ResponseService.readAuthToken(executor.callApiMethod(authenticationMethod));
    }

    public static String getTestRunId() {
        ExecutionService executor = new ExecutionService();
        PostTestRunStartMethod testRunStartMethod = new PostTestRunStartMethod();
        executor.expectStatus(testRunStartMethod, HTTPStatusCodeType.OK);
        return ResponseService.readId(executor.callApiMethod(testRunStartMethod));
    }

    public static String getTestId(String testRunId) {
        ExecutionService executor = new ExecutionService();
        PostTestExecutionStartMethod testExecutionStartMethod = new PostTestExecutionStartMethod(testRunId);
        executor.expectStatus(testExecutionStartMethod, HTTPStatusCodeType.OK);
        return ResponseService.readId(executor.callApiMethod(testExecutionStartMethod));
    }
}

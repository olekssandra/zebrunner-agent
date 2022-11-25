package api;

import api.enums.HTTPStatusCodeType;
import api.methods.PostAuthenticationMethod;
import api.methods.PostTestExecutionStartMethod;
import api.methods.PostTestRunStartMethod;
import com.qaprosoft.carina.core.foundation.api.http.HttpResponseStatusType;
import io.restassured.path.json.JsonPath;

public class AuthService {

    private static String authToken;

    public static String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        AuthService.authToken = authToken;
    }

    public static void refreshAuthToken() {
        PostAuthenticationMethod postAuthTokenMethod = new PostAuthenticationMethod();
        postAuthTokenMethod.expectResponseStatus(HttpResponseStatusType.OK_200);
        postAuthTokenMethod.callAPI();
        authToken = JsonPath.from("src/test/resources/api/authentication/_post/rs.json").get("authToken");
    }

    public static String getTestRunId() {
        ExecutionService executor = new ExecutionService();
        PostTestRunStartMethod testRunStartMethod = new PostTestRunStartMethod();
        executor.expectStatus(testRunStartMethod, HTTPStatusCodeType.OK);
        return JsonPath.from(executor.callApiMethod(testRunStartMethod)).get("id").toString();
    }

    public static String getTestId(String testRunId) {
        ExecutionService executor = new ExecutionService();
        PostTestExecutionStartMethod testExecutionStartMethod = new PostTestExecutionStartMethod(testRunId);
        executor.expectStatus(testExecutionStartMethod, HTTPStatusCodeType.OK);
        testExecutionStartMethod.callAPI();
        return JsonPath.from(executor.callApiMethod(testExecutionStartMethod)).get("id").toString();
    }
}

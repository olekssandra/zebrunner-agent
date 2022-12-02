package api;

import api.enums.HTTPStatusCodeType;
import api.methods.PostAuthenticationMethod;
import api.methods.PostTestExecutionStartMethod;
import api.methods.PostTestRunStartMethod;
import com.qaprosoft.carina.core.foundation.crypto.CryptoTool;
import io.restassured.path.json.JsonPath;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

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
        authToken = JsonPath.from(executor.callApiMethod(authenticationMethod)).get("authToken").toString();
        authenticationMethod.validateResponse();
        Properties properties = new Properties();
        CryptoTool cryptoTool = new CryptoTool();
        String str = cryptoTool.encrypt(authToken);
        properties.put("auth_token", "{crypt:" + str + "}");
        String path = "src/main/resources/_testdata.properties";
        FileOutputStream outputStream = new FileOutputStream(path);
        properties.store(outputStream, null);
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

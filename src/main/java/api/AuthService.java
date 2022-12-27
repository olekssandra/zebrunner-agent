package api;

import api.enums.HTTPStatusCodeType;
import api.methods.PostAuthenticationMethod;
import io.restassured.path.json.JsonPath;

public class AuthService {

    private static String authToken;

    public static String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String response) {
        this.authToken = JsonPath.from(response).get(JsonConstant.AUTH_TOKEN).toString();
    }

    public static void refreshAuthToken() {
        ExecutionService executor = new ExecutionService();
        PostAuthenticationMethod authenticationMethod = new PostAuthenticationMethod();
        executor.expectStatus(authenticationMethod, HTTPStatusCodeType.OK);
        AuthService authService = new AuthService();
        authService.setAuthToken(executor.callApiMethod(authenticationMethod));
    }
}

package api;

import api.enums.HTTPStatusCodeType;
import api.methods.PostAuthenticationMethod;

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
        ResponseService responseService = new ResponseService();
        responseService.setAuthToken(executor.callApiMethod(authenticationMethod));
        authToken = responseService.getAuthToken();
    }
}

package api;

import io.restassured.path.json.JsonPath;

public class ResponseService {
    private static String authToken;
    private static String id;
    private static String testStatus;
    private static String result;

    public ResponseService() {
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static String readAuthToken(String response){
        return authToken = JsonPath.from(response).get(JsonConstant.AUTH_TOKEN).toString();
    }

    public static String readId(String response){
        return id = JsonPath.from(response).get(JsonConstant.ID).toString();
    }

    public static String readTestStatus(String response){
        return testStatus = JsonPath.from(response).get(JsonConstant.TEST_STATUS).toString();
    }

    public static String readResult(String response){
        return result = JsonPath.from(response).get(JsonConstant.TEST_RESULT).toString();
    }
}

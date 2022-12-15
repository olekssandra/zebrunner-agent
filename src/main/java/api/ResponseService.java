package api;

import io.restassured.path.json.JsonPath;

public class ResponseService {
    private String authToken;
    private String id;
    private String testStatus;
    private String result;

    public ResponseService() {
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String response) {
        this.authToken = JsonPath.from(response).get(JsonConstant.AUTH_TOKEN).toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String response) {
        this.id = JsonPath.from(response).get(JsonConstant.ID).toString();
    }

    public String getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(String response) {
        this.testStatus = JsonPath.from(response).get(JsonConstant.TEST_STATUS).toString();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String response) {
        this.result = JsonPath.from(response).get(JsonConstant.TEST_RESULT).toString();
    }
}

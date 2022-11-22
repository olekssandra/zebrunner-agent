package api;

import api.enums.HTTPStatusCodeType;
import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import io.restassured.response.Response;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class ExecutionService {
    public void expectStatus(AbstractApiMethodV2 method, HTTPStatusCodeType status) {
        method.getRequest().expect().statusCode(status.getStatusCode());
    }

    public String callApiMethod(AbstractApiMethodV2 method) {
        Response response = method.callAPI();
        return response.asString().isEmpty() ? null : response.asString();
    }

    public void validateResponse(AbstractApiMethodV2 method, JSONCompareMode mode, String... validationFlags) {
        method.validateResponse(mode, validationFlags);
    }
}

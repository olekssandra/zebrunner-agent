package api.methods;

import api.AuthService;
import com.qaprosoft.carina.core.foundation.utils.R;

public class PutTestRunExecutionFinishMethod extends BaseApiMethod {
    public PutTestRunExecutionFinishMethod() {
        super("api/test_run/_put/rq.json", "api/test_run/_put/rs.json");
        replaceUrlPlaceholder("base_url",  R.CONFIG.get("api_url"));
        replaceUrlPlaceholder("id", AuthService.getTestRunId());
        setHeaders("Authorization=Bearer "+ R.TESTDATA.getDecrypted("auth_token"));
    }

    public PutTestRunExecutionFinishMethod(String testRunId) {
        super("api/test_run/_put/rq.json", "api/test_run/_put/rs.json");
        replaceUrlPlaceholder("base_url",  R.CONFIG.get("api_url"));
        replaceUrlPlaceholder("id", testRunId);
        setHeaders("Authorization=Bearer "+ R.TESTDATA.getDecrypted("auth_token"));
    }
}

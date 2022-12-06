package api.methods;

import api.AuthService;
import api.enums.TestStatuses;
import com.qaprosoft.carina.core.foundation.utils.R;

import java.util.Properties;

public class PutTestExecutionFinishMethod extends BaseApiMethod {
    public PutTestExecutionFinishMethod() {
        super("api/test_execution/_put/rq.json", "api/test_execution/_put/rs.json");
        replaceUrlPlaceholder("base_url",  R.CONFIG.get("api_url"));
        String testRunId=AuthService.getTestRunId();
        replaceUrlPlaceholder("testRunId", testRunId);
        replaceUrlPlaceholder("testId", AuthService.getTestId(testRunId));
    }

    public PutTestExecutionFinishMethod(String testRunId, String testId) {
        super("api/test_execution/_put/rq.json", "api/test_execution/_put/rs.json");
        replaceUrlPlaceholder("base_url",  R.CONFIG.get("api_url"));
        replaceUrlPlaceholder("testRunId", testRunId);
        replaceUrlPlaceholder("testId", testId);
    }
}

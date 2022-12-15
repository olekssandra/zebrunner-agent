package api.methods;

import com.qaprosoft.carina.core.foundation.utils.R;

public class PutTestExecutionFinishMethod extends BaseApiMethod {
    public PutTestExecutionFinishMethod(String testRunId, String testId, String result) {
        super("api/test_execution/_put/rq.json", "api/test_execution/_put/rs.json");
        replaceUrlPlaceholder("base_url", R.CONFIG.get("api_url"));
        replaceUrlPlaceholder("testRunId", testRunId);
        replaceUrlPlaceholder("testId", testId);
        addProperty("result", result);
    }
}

package api.methods;

import com.qaprosoft.carina.core.foundation.utils.R;

public class PutTestRunExecutionFinishMethod extends BaseApiMethod {
    public PutTestRunExecutionFinishMethod(String testRunId) {
        super("api/test_run/_put/rq.json", "api/test_run/_put/rs.json");
        replaceUrlPlaceholder("base_url",  R.CONFIG.get("api_url"));
        replaceUrlPlaceholder("id", testRunId);
    }
}

package api.methods;

import com.qaprosoft.carina.core.foundation.utils.R;

public class PostTestExecutionLogsMethod extends BaseApiMethod {
    public PostTestExecutionLogsMethod(String testRunId, String testId) {
        super("api/test_execution/post_logs/rq.json", "api/test_execution/post_logs/rs.json");
        replaceUrlPlaceholder("base_url", R.CONFIG.get("api_url"));
        replaceUrlPlaceholder("testRunId", testRunId);
        addProperty("testId", testId);
    }
}

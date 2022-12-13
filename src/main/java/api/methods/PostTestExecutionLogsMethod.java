package api.methods;

import api.ReportingService;
import com.qaprosoft.carina.core.foundation.utils.R;

public class PostTestExecutionLogsMethod extends BaseApiMethod {
    public PostTestExecutionLogsMethod(String testRunId) {
        super("api/test_execution/post_logs/rq.json", "api/test_execution/post_logs/rs.json");
        replaceUrlPlaceholder("base_url",  R.CONFIG.get("api_url"));
        replaceUrlPlaceholder("testRunId", testRunId);
        addProperty("testId", ReportingService.getInstance().getTestId());
    }
}

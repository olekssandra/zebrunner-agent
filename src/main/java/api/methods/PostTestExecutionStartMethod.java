package api.methods;

import com.qaprosoft.carina.core.foundation.utils.R;

public class PostTestExecutionStartMethod extends BaseApiMethod {
    public PostTestExecutionStartMethod(String testRunId) {
        super("api/test_execution/_post/rq.json", "api/test_execution/_post/rs.json");
        replaceUrlPlaceholder("base_url", R.CONFIG.get("api_url"));
        replaceUrlPlaceholder("testRunId", testRunId);
    }
}

package api.methods;

import com.qaprosoft.carina.core.foundation.utils.R;

public class PutTestExecutionLabelsMethod extends BaseApiMethod {
    public PutTestExecutionLabelsMethod(String testRunId, String testId) {
        super("api/test_execution/put_labels/rq.json", null);
        replaceUrlPlaceholder("base_url",  R.CONFIG.get("api_url"));
        replaceUrlPlaceholder("testRunId", testRunId);
        replaceUrlPlaceholder("testId", testId);
    }
}

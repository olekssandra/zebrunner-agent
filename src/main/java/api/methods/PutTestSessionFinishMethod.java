package api.methods;

import com.qaprosoft.carina.core.foundation.utils.R;

public class PutTestSessionFinishMethod extends BaseApiMethod {
    public PutTestSessionFinishMethod(String testRunId, String testSessionId, String testId) {
        super("api/test_session/_put/rq.json", "api/test_session/_put/rs.json");
        replaceUrlPlaceholder("base_url", R.CONFIG.get("api_url"));
        replaceUrlPlaceholder("testRunId", testRunId);
        replaceUrlPlaceholder("testSessionId", testSessionId);
        addProperty("testIds", testId);
    }
}

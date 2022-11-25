package api.methods;

import com.qaprosoft.carina.core.foundation.utils.R;

public class PostTestSessionCompleteMethod extends BaseApiMethod {
    public PostTestSessionCompleteMethod(String testRunId) {
        super("api/test_session/_post/rq.json", "api/test_session/_post/rs.json");
        replaceUrlPlaceholder("base_url", R.CONFIG.get("api_url"));
        replaceUrlPlaceholder("testRunId", testRunId);
        setHeaders("Authorization=Bearer " + R.TESTDATA.getDecrypted("auth_token"));
    }
}
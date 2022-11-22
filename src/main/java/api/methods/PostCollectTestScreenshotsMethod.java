package api.methods;

import api.AuthService;
import com.qaprosoft.carina.core.foundation.utils.R;

import java.util.Properties;

public class PostCollectTestScreenshotsMethod extends BaseApiMethod {
    public PostCollectTestScreenshotsMethod() {
        super("api/test_execution/post_screenshots/rq.json", "api/test_execution/post_screenshots/rs.json", new Properties());
        replaceUrlPlaceholder("base_url",  R.CONFIG.get("base_url"));
        replaceUrlPlaceholder("testRunId", AuthService.getTestRunId());
        request.header("Authorization", "Bearer "+ R.TESTDATA.getDecrypted("auth_token"));
    }
}

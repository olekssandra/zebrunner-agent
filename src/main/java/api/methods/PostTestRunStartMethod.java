package api.methods;

import api.AuthService;
import com.qaprosoft.carina.core.foundation.utils.R;

public class PostTestRunStartMethod extends BaseApiMethod {
    public PostTestRunStartMethod() {
        super("api/test_run/_post/rq.json", "api/test_run/_post/rs.json");
        replaceUrlPlaceholder("base_url",  R.CONFIG.get("api_url"));
        addUrlParameter("projectKey","CR3");
        setHeaders("Authorization=Bearer "+ R.TESTDATA.getDecrypted("auth_token"));
    }
}

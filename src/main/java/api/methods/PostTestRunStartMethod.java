package api.methods;

import com.qaprosoft.carina.core.foundation.utils.R;

public class PostTestRunStartMethod extends BaseApiMethod {
    public PostTestRunStartMethod() {
        super("api/test_run/_post/rq.json", "api/test_run/_post/rs.json");
        replaceUrlPlaceholder("base_url",  R.CONFIG.get("api_url"));
        addUrlParameter("projectKey","CR3");
    }
}

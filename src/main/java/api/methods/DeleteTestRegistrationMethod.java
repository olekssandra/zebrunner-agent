package api.methods;

import api.ReportingService;
import com.qaprosoft.carina.core.foundation.utils.R;

public class DeleteTestRegistrationMethod extends BaseApiMethod {
    public DeleteTestRegistrationMethod(String testRunId, String testId) {
        super(null, null);
        replaceUrlPlaceholder("base_url", R.CONFIG.get("api_url"));
        replaceUrlPlaceholder("testRunId", testRunId);
        replaceUrlPlaceholder("testId", testId);
    }
}

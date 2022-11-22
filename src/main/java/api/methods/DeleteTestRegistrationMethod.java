package api.methods;

import api.AuthService;
import com.qaprosoft.carina.core.foundation.utils.R;

public class DeleteTestRegistrationMethod extends BaseApiMethod {
    public DeleteTestRegistrationMethod() {
        super(null, null);
        replaceUrlPlaceholder("base_url",  R.CONFIG.get("api_url"));
        String testRunId=AuthService.getTestRunId();
        replaceUrlPlaceholder("testRunId", testRunId);
        replaceUrlPlaceholder("testId", AuthService.getTestId(testRunId));
        setHeaders("Authorization=Bearer "+ R.TESTDATA.getDecrypted("auth_token"));
    }
}

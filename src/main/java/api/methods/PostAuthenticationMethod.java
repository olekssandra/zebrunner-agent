package api.methods;

import com.qaprosoft.carina.core.foundation.utils.Configuration;
import com.qaprosoft.carina.core.foundation.utils.R;

import java.util.Properties;

public class PostAuthenticationMethod extends BaseApiMethod {
    public PostAuthenticationMethod() {
        super("api/authentication/_post/rq.json", "api/authentication/_post/rs.json", new Properties());
        replaceUrlPlaceholder("base_url", R.CONFIG.get("api_url"));
    }
}

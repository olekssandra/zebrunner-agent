package api.methods;

import com.qaprosoft.carina.core.foundation.utils.R;

public class PostAuthenticationMethod extends BaseApiMethod {
    public PostAuthenticationMethod() {
        super("api/authentication/_post/rq.json", "api/authentication/_post/rs.json");
        replaceUrlPlaceholder("base_url", R.CONFIG.get("api_url"));
    }
}

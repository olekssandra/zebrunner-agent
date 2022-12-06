package api.methods;

import api.JsonConstant;
import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.carina.core.foundation.utils.R;

import java.util.Properties;

public abstract class BaseApiMethod extends AbstractApiMethodV2 {

    public BaseApiMethod(String rqPath, String rsPath, String propertiesPath) {
        super(rqPath, rsPath, propertiesPath);
    }

    public BaseApiMethod(String rqPath, String rsPath, Properties properties) {
        super(rqPath, rsPath, properties);
        setAuth();
    }

    public BaseApiMethod(String rqPath, String rsPath) {
        super(rqPath, rsPath);
        setAuth();
    }

    private void setAuth() {
        setHeaders("Authorization=Bearer "+ R.TESTDATA.getDecrypted(JsonConstant.AUTH_TOKEN));
    }
}

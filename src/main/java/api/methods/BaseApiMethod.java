package api.methods;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;

import java.util.Properties;

public abstract class BaseApiMethod extends AbstractApiMethodV2 {

    public BaseApiMethod(String rqPath, String rsPath, String propertiesPath) {
        super(rqPath, rsPath, propertiesPath);
        setAuth();
    }

    public BaseApiMethod(String rqPath, String rsPath, Properties properties) {
        super(rqPath, rsPath, properties);
        //setAuth();
    }

    public BaseApiMethod(String rqPath, String rsPath) {
        super(rqPath, rsPath);
        //setAuth();
    }

    private void setAuth() {
      //  setHeaders(String.format("Authorization=%s %s", AuthorizationType.BEARER.getType(), new AuthService().getAuthToken()));
    }
}

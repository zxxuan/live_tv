package com.me.livetv.domain;

/**
 * Created by zhangxuan on 2016/3/25.
 */
public class LoginResponseData {

    /**
     * access_token : d4c57340-e17e-44a0-9876-021ea6170eaf
     * token_type : bearer
     * refresh_token : 448dd15a-6b8d-4908-bb7f-ca6f50efb259
     * expires_in : 43199
     * scope : read write
     */

    private String access_token;
    private String token_type;
    private String refresh_token;
    private String expires_in;
    private String scope;

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public String getScope() {
        return scope;
    }
}

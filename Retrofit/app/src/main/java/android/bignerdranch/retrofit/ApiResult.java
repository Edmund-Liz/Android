package android.bignerdranch.retrofit;

public class ApiResult{
    private String msg;
    private String token;


    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString(){
        return "ApiResult {"  +
                "msg="+msg+
                "token="+token;
    }
}

import com.alibaba.fastjson.JSONObject;
import com.alibaba.ocean.rawsdk.ApiExecutor;
import com.alibaba.ocean.rawsdk.client.exception.OceanException;
import com.umeng.uapp.param.UmengUappGetNewAccountsParam;
import com.umeng.uapp.param.UmengUappGetNewAccountsResult;


public class TestOpenAPI {
    public void umengUappGetNewAccounts(ApiExecutor apiExecutor) {
        UmengUappGetNewAccountsParam param = new UmengUappGetNewAccountsParam();
        // 测试环境只支持http
        // param.getOceanRequestPolicy().setUseHttps(false);
        param.setAppkey("");
        param.setStartDate("2018-01-01");
        param.setEndDate("");
        param.setPeriodType("");
        param.setChannel("");

        try {
            UmengUappGetNewAccountsResult result = apiExecutor.execute(param);
            System.out.println(JSONObject.toJSONString(result));
        } catch (OceanException e) {
            System.out.println("errorCode=" + e.getErrorCode() + ", errorMessage=" + e.getErrorMessage());
        }
    }

    public static void main(String[] args) {
        // 请替换apiKey和apiSecurity
        ApiExecutor apiExecutor = new ApiExecutor("6626817", "SoG05Y7TTkrb");
        apiExecutor.setServerHost("gateway.open.umeng.com");

        TestOpenAPI testOpenAPI = new TestOpenAPI();
        testOpenAPI.umengUappGetNewAccounts(apiExecutor);
    }
}

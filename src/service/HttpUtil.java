package service;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by shishi on 12/20/17.
 */
public class HttpUtil {

    private static final CloseableHttpClient httpclient = HttpClients.createDefault();

    private static final String url = "https://b832b50e-7d53-447f-8604-a3c650c3a656.pushnotifications.pusher.com/publish_api/v1/instances/b832b50e-7d53-447f-8604-a3c650c3a656/publishes";

    private String getAuthHeader() {
        String authHeader = "Bearer 4E4A41E88BB91E54FBEFA69F3835FA6";
        return authHeader;
    }


    /**
     * Send post request
     * @param jsonObj
     * @return
     */
    public  String sendPost( JSONObject jsonObj) {

        //UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);

        StringEntity entity = new StringEntity(jsonObj.toString(), Consts.UTF_8);
        //StringEntity entity = new StringEntity(jsonObj, Consts.UTF_8);
        entity.setContentType("application/json");

        HttpPost httppost = new HttpPost(url);
        httppost.addHeader("Content-Type", "application/json");
        httppost.addHeader("Authorization", getAuthHeader());
        httppost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity1 = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(entity1);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public JSONObject convertToJson(String email,String dueDate){
       try {

            JSONObject jsonObj;
            String jsonStr = "{\"interests\":["+email+"],\"apns\":{\"aps\":{\"alert\":{\"title\":\"Book Due Alert\",\"body\":\"Your book will due within 5 days!\"}}}}";
            jsonObj = new JSONObject(jsonStr);
            return jsonObj;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



}

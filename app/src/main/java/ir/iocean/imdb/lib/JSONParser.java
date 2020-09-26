package ir.iocean.imdb.lib;

import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class JSONParser {
    private static InputStream is = null;
    private static JSONObject jObj = null;
     private static String json = "";
    private DefaultHttpClient httpClient;

    public JSONParser() {
        httpClient = new DefaultHttpClient();
    }

    public JSONObject getJSONFromUrl(String url, List<NameValuePair> params) {
        try {
            httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            httpPost.addHeader("X-REQUESTED-WITH", "XMLHttpRequest");
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
        }
        try {
            jObj = null;
          /*  jObj2 = null;
            Object item = stringToObject(json);
            if (item instanceof JSONArray)
            {
                // it's an array
                JSONArray urlArray = (JSONArray) item;
                // do all kinds of JSONArray'ish things with urlArray
            }
            else
            {
                // if you know it's either an array or an object, then it's an object
                JSONObject urlObject = (JSONObject) item;
                // do objecty stuff with urlObject
            }*/
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("Exception",e.getMessage());
        }
        return jObj;
    }
    public static Object stringToObject(String str) {
        try {
            return new ObjectInputStream(new Base64InputStream(
              new ByteArrayInputStream(str.getBytes()), Base64.NO_PADDING
              | Base64.NO_WRAP)).readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

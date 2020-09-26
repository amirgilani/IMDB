package ir.iocean.imdb.lib;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UserFunctions {

  public JSONParser jsonParser;
  public String Main = "http://www.omdbapi.com/";

  private Context ctx;
  private ConnectionDetector cd;

  public Toast toast;
  public Dialog dialog;

  public UserFunctions(Context context) {
    this.ctx = context;
    this.jsonParser = new JSONParser();
    this.cd = new ConnectionDetector(context);

  }

  // ********************************************* Other Function ********************************************************
  public boolean isConnectingToInternet() {
    if (isOnline()) return true;
   /* Intent i = new Intent(currentActivity, ActivityInternet.class);
    currentActivity.finish();
    currentActivity.startActivity(i);*/
    return false;

  }

  public boolean isOnline() {

    Runtime runtime = Runtime.getRuntime();
    try {

      Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
      int exitValue = ipProcess.waitFor();
      return (exitValue == 0);

    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return false;
  }

  public void toast(String msg) {
    toast = Toast.makeText(ctx, msg, Toast.LENGTH_LONG);
    LinearLayout toastLayout = (LinearLayout) toast.getView();
    TextView toastTV = (TextView) toastLayout.getChildAt(0);
    toastTV.setTextColor(Color.parseColor("#ffffff"));
    toastTV.setShadowLayer(0, 0, 0, Color.parseColor("#ffffff"));

    toast.show();
  }


  // ********************************************* JSON Request ********************************************************


  public JSONObject ListOfMain() {
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    return jsonParser.getJSONFromUrl(Main + "?apikey=3e974fca&s=batman", params);
  }

  public JSONObject MovieDetails(String id) {
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    return jsonParser.getJSONFromUrl(Main + "?apikey=3e974fca&i=" + id, params);
  }


}

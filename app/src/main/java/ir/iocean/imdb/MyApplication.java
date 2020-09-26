package ir.iocean.imdb;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;

import java.io.File;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;


public class MyApplication extends MultiDexApplication {

  public static LayoutInflater inflater;
  public static Context context;
  public static Activity currentActivity;
  public static final String TAG = MyApplication.class.getSimpleName();

  private static MyApplication mInstance;

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }


  @Override
  public void onCreate() {
    super.onCreate();
    context = getApplicationContext();
    inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    mInstance = this;


  }


  public static synchronized MyApplication getInstance() {
    return mInstance;
  }


}

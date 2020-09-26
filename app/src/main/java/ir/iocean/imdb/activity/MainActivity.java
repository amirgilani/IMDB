package ir.iocean.imdb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import ir.iocean.imdb.MyApplication;
import ir.iocean.imdb.R;
import ir.iocean.imdb.adapter.MainListAdapter;
import ir.iocean.imdb.lib.SessionManager;
import ir.iocean.imdb.lib.UserFunctions;
import ir.iocean.imdb.model.MainMovieModel;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.downloader.Progress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  ArrayList<MainMovieModel> mainMovieModels = new ArrayList<>();
  ArrayList<String> imdbIDs = new ArrayList<>();
  JSONObject listDetails = new JSONObject();
  UserFunctions userFunctions;
  SessionManager session;
  String dirPath = "";
  MainListAdapter mainListAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    MyApplication.currentActivity = this;
    dirPath = getRootDirPath(getApplicationContext());
    userFunctions = new UserFunctions(this);
    session = new SessionManager(this);
    mainListAdapter = new MainListAdapter(mainMovieModels, userFunctions, dirPath);

    PRDownloader.initialize(getApplicationContext());
    PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
      .setDatabaseEnabled(true)
      .build();
    PRDownloader.initialize(getApplicationContext(), config);

    ListView lvMovieLists = (ListView) findViewById(R.id.lvMovieLists);
    lvMovieLists.setAdapter(mainListAdapter);

    if (userFunctions.isConnectingToInternet()) {
      new ListOfObjects().execute();
    } else {
      offlineLoadData();
    }
  }

  private void offlineLoadData() {

    try {
      JSONObject json = new JSONObject(session.getListOfMain());
      JSONArray arr = json.getJSONArray("Search");
      for (int i = 0; i < arr.length(); i++) {
        JSONObject obj = arr.getJSONObject(i);
        downloadImageFromUrl(obj.getString("Poster"), obj.getString("imdbID"));
        imdbIDs.add(obj.getString("imdbID"));
        MainMovieModel mainMovieModel = new MainMovieModel();
        mainMovieModel.setId(obj.getString("imdbID"));
        mainMovieModel.setTitle(obj.getString("Title"));
        mainMovieModel.setDescription(obj.getString("Year") + " Official " + obj.getString("Type"));
        mainMovieModel.setPosterImage(obj.getString("Poster"));
        mainMovieModels.add(mainMovieModel);
      }
      mainListAdapter.notifyDataSetChanged();
    } catch (Exception e) {
      Log.e("offlineLoadData", e.getMessage());
    }


  }

  public static String getRootDirPath(Context context) {
    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
      File file = ContextCompat.getExternalFilesDirs(context.getApplicationContext(),
        null)[0];
      return file.getAbsolutePath();
    } else {
      return context.getApplicationContext().getFilesDir().getAbsolutePath();
    }
  }

  private void downloadImageFromUrl(String url, String fileName) {

    PRDownloader.download(url, dirPath, fileName)
      .build()
      .setOnStartOrResumeListener(new OnStartOrResumeListener() {
        @Override
        public void onStartOrResume() {

        }
      })
      .setOnPauseListener(new OnPauseListener() {
        @Override
        public void onPause() {

        }
      })
      .setOnCancelListener(new OnCancelListener() {
        @Override
        public void onCancel() {

        }
      })

      .setOnProgressListener(new OnProgressListener() {
        @Override
        public void onProgress(Progress progress) {

        }
      })
      .start(new OnDownloadListener() {
        @Override
        public void onDownloadComplete() {

        }

        @Override
        public void onError(Error error) {

        }
      });

  }

  private class ListOfObjects extends AsyncTask<String, String, JSONObject> {
    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... args) {
      try {
        return userFunctions.ListOfMain();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return null;
    }

    @Override
    protected void onPostExecute(JSONObject json) {
      try {

        if (json != null) {
          session.setListOfMain(json.toString());

          JSONArray arr = json.getJSONArray("Search");
          for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            downloadImageFromUrl(obj.getString("Poster"), obj.getString("imdbID"));
            imdbIDs.add(obj.getString("imdbID"));
            MainMovieModel mainMovieModel = new MainMovieModel();
            mainMovieModel.setId(obj.getString("imdbID"));
            mainMovieModel.setTitle(obj.getString("Title"));
            mainMovieModel.setDescription(obj.getString("Year") + " Official " + obj.getString("Type"));
            mainMovieModel.setPosterImage(obj.getString("Poster"));
            mainMovieModels.add(mainMovieModel);
          }
          mainListAdapter.notifyDataSetChanged();
          new GetAllDetail().execute();
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
  }

  private class GetAllDetail extends AsyncTask<String, String, JSONObject> {
    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... args) {
      try {
        return userFunctions.MovieDetails(imdbIDs.get(0));
      } catch (Exception e) {
        e.printStackTrace();
      }
      return null;
    }

    @Override
    protected void onPostExecute(JSONObject json) {
      try {

        if (json != null) {


          listDetails.put(imdbIDs.get(0), json.toString());
          imdbIDs.remove(0);
          if (imdbIDs.size() > 0) {
            new GetAllDetail().execute();
            session.setMovieDetail(listDetails.toString());
          }

        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
  }
}

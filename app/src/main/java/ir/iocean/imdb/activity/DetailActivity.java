package ir.iocean.imdb.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import ir.iocean.imdb.R;
import ir.iocean.imdb.lib.SessionManager;

public class DetailActivity extends AppCompatActivity {


  ImageView imgPosterBackground, imgPoster;
  TextView tvTitle, tvGenre, tvRuntime, tvDescription, tvAwards, tvRates, tvRateCount, tvMetaScore;
  SessionManager session;
  String imdbID, dirPath;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    SessionManager session = new SessionManager(this);
    dirPath = getRootDirPath(getApplicationContext());
    views();

    Intent intent = getIntent();
    Bundle extera = intent.getExtras();
    if (extera != null) {
      imdbID = extera.getString("imdbID");
    }

    try {

      JSONObject movieDetails = new JSONObject(session.getMovieDetail());
      JSONObject getDetails = new JSONObject(movieDetails.getString(imdbID));


      tvTitle.setText(getDetails.getString("Title") + "(" + getDetails.getString("Year") + ")");
      tvGenre.setText(getDetails.getString("Genre"));
      tvRuntime.setText(getDetails.getString("Runtime"));
      tvDescription.setText(getDetails.getString("Plot") + "\n" + getDetails.getString("Actors") + "\n" + getDetails.getString("Writer"));
      tvAwards.setText(getDetails.getString("Awards"));
      tvRates.setText(getDetails.getString("imdbRating") + " / 10");
      tvRateCount.setText(getDetails.getString("imdbVotes"));
      tvMetaScore.setText(getDetails.getString("Metascore"));

      imgPoster.setImageBitmap(BitmapFactory.decodeFile(dirPath + "/" + imdbID));
      imgPosterBackground.setImageBitmap(BitmapFactory.decodeFile(dirPath + "/" + imdbID));

    } catch (Exception e) {
      Log.e("movieDetails", e.getMessage());
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

  private void views() {
    imgPosterBackground = (ImageView) findViewById(R.id.imgPosterBackground);
    imgPoster = (ImageView) findViewById(R.id.imgPoster);

    tvTitle = (TextView) findViewById(R.id.tvTitle);
    tvGenre = (TextView) findViewById(R.id.tvGenre);
    tvRuntime = (TextView) findViewById(R.id.tvRuntime);
    tvDescription = (TextView) findViewById(R.id.tvDescription);
    tvAwards = (TextView) findViewById(R.id.tvAwards);
    tvRates = (TextView) findViewById(R.id.tvRates);
    tvRateCount = (TextView) findViewById(R.id.tvRateCount);
    tvMetaScore = (TextView) findViewById(R.id.tvMetaScore);
  }
}

package ir.iocean.imdb.lib;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by Amir on 03/07/2016.
 */
public class SessionManager {
  // LogCat tag
  private static String TAG = SessionManager.class.getSimpleName();

  // Shared Preferences
  SharedPreferences pref;

  Editor editor;
  Context _context;

  // Shared pref mode
  int PRIVATE_MODE = 0;

  // Shared preferences file name
  private static final String PREF_NAME = "imdb";

  private static final String LIST_OF_MAIN = "listofmain";
  private static final String MOVIE_DETAIL = "moviedetail";

  public SessionManager(Context context) {
    this._context = context;
    pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    editor = pref.edit();
  }


  public void setListOfMain(String lists) {

    editor.putString(LIST_OF_MAIN, lists);

    editor.commit();

  }

  public String getListOfMain() {
    return pref.getString(LIST_OF_MAIN, "");
  }


  public void setMovieDetail(String lists) {

    editor.putString(MOVIE_DETAIL, lists);

    editor.commit();

  }

  public String getMovieDetail() {
    return pref.getString(MOVIE_DETAIL, "");
  }


}
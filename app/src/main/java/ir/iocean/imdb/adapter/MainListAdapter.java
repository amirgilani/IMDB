
package ir.iocean.imdb.adapter;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import ir.iocean.imdb.MyApplication;
import ir.iocean.imdb.R;
import ir.iocean.imdb.activity.DetailActivity;
import ir.iocean.imdb.lib.UserFunctions;
import ir.iocean.imdb.model.MainMovieModel;

import static ir.iocean.imdb.MyApplication.context;


public class MainListAdapter extends ArrayAdapter<MainMovieModel> {

  UserFunctions userFunctions;
  String dirPath;


  public MainListAdapter(ArrayList<MainMovieModel> array, UserFunctions userFunctions, String dirPath) {
    super(context, R.layout.main_list_item, array);
    this.userFunctions = userFunctions;
    this.dirPath = dirPath;
  }


  private class ViewHolder {


    public TextView tvTitle;
    public TextView tvDescription;
    public ImageView imgPoster;
    public ImageView imgPosterBackground;
    public CardView cvMain;

    public ViewHolder(View view) {
      tvTitle = (TextView) view.findViewById(R.id.tvTitle);
      tvDescription = (TextView) view.findViewById(R.id.tvDescription);
      imgPoster = (ImageView) view.findViewById(R.id.imgPoster);
      imgPosterBackground = (ImageView) view.findViewById(R.id.imgPosterBackground);
      cvMain = (CardView) view.findViewById(R.id.cvMain);


    }


    public void fill(final ArrayAdapter<MainMovieModel> adapter, final MainMovieModel item, final int position) {
      tvTitle.setText(item.getTitle());
      tvDescription.setText(item.getDescription());

      if (userFunctions.isConnectingToInternet()) {
        Picasso.with(MyApplication.currentActivity).load(item.getPosterImage()).into(imgPoster);
        Picasso.with(MyApplication.currentActivity).load(item.getPosterImage()).into(imgPosterBackground);
      } else {
        imgPoster.setImageBitmap(BitmapFactory.decodeFile(dirPath + "/" + item.getId()));
        imgPosterBackground.setImageBitmap(BitmapFactory.decodeFile(dirPath + "/" + item.getId()));

      }
      cvMain.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          MyApplication.currentActivity.startActivity(new Intent(MyApplication.currentActivity, DetailActivity.class).putExtra("imdbID", item.getId()));
        }
      });
    }
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;

    MainMovieModel item = getItem(position);
    if (convertView == null) {
      convertView = MyApplication.inflater.inflate(R.layout.main_list_item, parent, false);
      holder = new ViewHolder(convertView);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }

    holder.fill(this, item, position);
    return convertView;
  }


}


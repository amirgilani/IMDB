package ir.iocean.imdb.model;

/**
 * Created by AmirGIlani on 3/31/18.
 */

public class MainMovieModel {
  private String id;
  private String posterImage;
  private String title;
  private String description;


  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setPosterImage(String posterImage) {
    this.posterImage = posterImage;
  }

  public String getPosterImage() {
    return posterImage;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

}

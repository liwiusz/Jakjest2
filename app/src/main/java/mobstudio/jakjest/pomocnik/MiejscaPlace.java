package mobstudio.jakjest.pomocnik;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Michał on 2016-07-25.
 */
public class MiejscaPlace {
    private String id;
    private String name;
    private String miasto;
    private String adres;
    private String kod;
    private double mapX;
    private double mapY;
    private String image;
    private String like;

    public String getIdEventFirst() {
        return idEventFirst;
    }

    public void setIdEventFirst(String idEventFirst) {
        this.idEventFirst = idEventFirst;
    }

    private String idEventFirst;


    private Bitmap imageView;



    public MiejscaPlace() {

    }

    public Bitmap getImageView() {
        return imageView;
    }

    public void setImageView(Bitmap imageView) {
        this.imageView = imageView;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public double getMapX() {
        return mapX;
    }

    public void setMapX(double mapX) {
        this.mapX = mapX;
    }

    public double getMapY() {
        return mapY;
    }

    public void setMapY(double mapY) {
        this.mapY = mapY;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }



}

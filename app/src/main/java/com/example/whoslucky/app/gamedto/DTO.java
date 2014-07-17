package com.example.whoslucky.app.gamedto;

/**
 * Created by Saurav on 7/17/2014.
 */
public class DTO {
    private int image;
    private int id;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DTO(int image, int id) {

        this.image = image;
        this.id = id;
    }
}

package com.sms.pruebamnc;

public class GridItem {
    private String id;
    private String image;
    private String title;
    private  String username;
    private String likes;
    private String favorito;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public GridItem() {
        super();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public void setFavorito(String favorito) {
        this.favorito = favorito;
    }

    public String getFavorito() {
        return favorito;
    }
}

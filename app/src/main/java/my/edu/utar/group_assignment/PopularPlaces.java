package my.edu.utar.group_assignment;

//DONE BY OOI ZHENG YEE


public class PopularPlaces {

    private String cityName;
    private String country;
    private String picURL;
    private String description;

    public PopularPlaces(String cityName, String country, String picURL, String description){
        this.cityName= cityName;
        this.country = country;
        this.picURL = picURL;
        this.description = description;
    }

    public String getCityName(){
        return cityName;
    }

    public void setCityName(){
        this.cityName = cityName;
    }

    public String getCountry(){
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

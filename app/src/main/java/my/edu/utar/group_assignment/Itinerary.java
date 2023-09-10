package my.edu.utar.group_assignment;

//DONE BY WU JIAN WEI


public class Itinerary {
    String placeName;
    String planDetail;

    public Itinerary(String placeName,String planDetail) {
        this.placeName = placeName;
        this.planDetail = planDetail;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPlanDetail() {
        return planDetail;
    }
}

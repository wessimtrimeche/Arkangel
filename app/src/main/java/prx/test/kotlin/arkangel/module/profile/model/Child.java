package prx.test.kotlin.arkangel.module.profile.model;

/**
 * Created by wessim23 on 3/14/18.
 */

public class Child {

    private String Name;



    private String parentCode;
    private  double longitude,latitude,batteryState;



    public String getName() {
        return Name;
    }


    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getBatteryState() {
        return batteryState;
    }

    public void setName(String Name) {

        this.Name = Name;
    }


    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

}

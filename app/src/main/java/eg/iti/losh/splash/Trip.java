package eg.iti.losh.splash;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

/**
 * Created by adel on 31/03/18.
 */

public class Trip extends Calendar {
    private String name;
    private String start_point;
    private String end_point;
    private Calendar start_date;
    private Calendar return_date;
    private String notes;
    private boolean round;

   private double Start_Lat;
   private double Start_Long;
   private double End_Lat;

    public double getStart_Lat() {
        return Start_Lat;
    }

    public void setStart_Lat(double start_Lat) {
        Start_Lat = start_Lat;
    }

    public double getStart_Long() {
        return Start_Long;
    }

    public void setStart_Long(double start_Long) {
        Start_Long = start_Long;
    }

    public double getEnd_Lat() {
        return End_Lat;
    }

    public void setEnd_Lat(double end_Lat) {
        End_Lat = end_Lat;
    }

    public double getEnd_Long() {
        return End_Long;
    }

    public void setEnd_Long(double end_Long) {
        End_Long = end_Long;
    }

    private double End_Long;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_point() {
        return start_point;
    }

    public void setStart_point(String start_point) {
        this.start_point = start_point;
    }

    public String getEnd_point() {
        return end_point;
    }

    public void setEnd_point(String end_point) {
        this.end_point = end_point;
    }

    public Calendar getStart_date() {
        return start_date;
    }

    public void setStart_date(Calendar start_date) {
        this.start_date = start_date;
    }

    public Calendar getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Calendar return_date) {
        this.return_date = return_date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isRound() {
        return round;
    }

    public void setRound(boolean b) {
        this.round = round;
    }

    @Override
    protected void computeTime() {

    }

    @Override
    protected void computeFields() {

    }

    @Override
    public void add(int i, int i1) {

    }

    @Override
    public void roll(int i, boolean b) {

    }

    @Override
    public int getMinimum(int i) {
        return 0;
    }

    @Override
    public int getMaximum(int i) {
        return 0;
    }

    @Override
    public int getGreatestMinimum(int i) {
        return 0;
    }

    @Override
    public int getLeastMaximum(int i) {
        return 0;
    }
}

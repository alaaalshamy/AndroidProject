package eg.iti.losh.splash;

/**
 * Created by adel on 26/03/18.
 */

public class TripDB {
    private String id;
    private String Name;
    private String Start_Address;
    private String End_Address;
    private String Notes;
    private boolean Round_Trip;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStart_Address() {
        return Start_Address;
    }

    public void setStart_Address(String start_Address) {
        Start_Address = start_Address;
    }

    public String getEnd_Address() {
        return End_Address;
    }

    public void setEnd_Address(String end_Address) {
        End_Address = end_Address;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public boolean isRound_Trip() {
        return Round_Trip;
    }

    public void setRound_Trip(boolean round_Trip) {
        Round_Trip = round_Trip;
    }

    public int[] getStart_Point() {
        return Start_Point;
    }

    public void setStart_Point(int[] start_Point) {
        Start_Point = start_Point;
    }

    public int[] getEnd_Point() {
        return End_Point;
    }

    public void setEnd_Point(int[] end_Point) {
        End_Point = end_Point;
    }

    private int[] Start_Point;
    private int[] End_Point;
}

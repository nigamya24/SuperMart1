package SuperMart;

import javax.lang.model.element.Name;

public class AttendanceTable {
    String Name;
    String Status;
    String DatenTime;

    AttendanceTable(String Name, String Status, String DatenTime){
        this.Name = Name;
        this.Status = Status;
        this.DatenTime = DatenTime;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDatenTime() {
        return DatenTime;
    }

    public void setDatenTime(String datenTime) {
        DatenTime = datenTime;
    }
}

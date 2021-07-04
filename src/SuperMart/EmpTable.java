package SuperMart;

public class EmpTable {

    String Name;
    String Mobile;
    String EmpID;
    String Salary;
    String Gender;
    String DOB;

    public EmpTable(String Name, String Mobile, String EmpID, String Salary, String Gender, String DOB){

        this.Name=Name;
        this.Mobile=Mobile;
        this.EmpID=EmpID;
        this.Salary=Salary;
        this.Gender=Gender;
        this.DOB=DOB;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmpID() {
        return EmpID;
    }

    public void setEmpID(String empID) {
        EmpID = empID;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String salary) {
        Salary = salary;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }
}

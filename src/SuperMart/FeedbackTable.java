package SuperMart;

public class FeedbackTable {

    String Name;
    String ContactNo;
    String Email;
    String Message;

    FeedbackTable(String Name, String ContactNo, String Email, String Message){

        this.Name = Name;
        this.ContactNo=ContactNo;
        this.Email=Email;
        this.Message=Message;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}

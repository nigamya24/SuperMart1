package SuperMart;

public class Vendortable {

    String Name;
    String Category;
    String Contact;

    Vendortable(String Name, String Category, String Contact){
        this.Name=Name;
        this.Category=Category;
        this.Contact=Contact;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }
}


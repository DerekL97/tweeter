package dao.dto;

public class UserDTO {

    private String alias;
    private String firstName;
    private String lastName;
    private String imageURL;

    public UserDTO() {
        this.imageURL = "https://www.imore.com/sites/imore.com/files/styles/large/public/field/image/2020/04/rodney-pic.png";
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageURL() {
        return imageURL;
    }
}

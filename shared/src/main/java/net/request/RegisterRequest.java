package net.request;

public class RegisterRequest extends Request{
    /**
     * The user's first name.
     */
    public String firstName;

    /**
     * The user's last name.
     */
    public String lastName;

    /**
     * The base-64 encoded bytes of the user's profile image.
     */
    public String image;

    /**
     * The user's username (or "alias" or "handle"). E.g., "@susan".
     */
    protected String username;

    /**
     * The user's password.
     */
    protected String password;

    public RegisterRequest() {
    }

    public RegisterRequest(String firstName, String lastName, String image, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getImage() {
        return image;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

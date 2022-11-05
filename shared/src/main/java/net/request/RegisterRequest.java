package net.request;

public class RegisterRequest {
    /**
     * The user's first name.
     */
    public final String firstName;

    /**
     * The user's last name.
     */
    public final String lastName;

    /**
     * The base-64 encoded bytes of the user's profile image.
     */
    public final String image;

    /**
     * The user's username (or "alias" or "handle"). E.g., "@susan".
     */
    protected final String username;

    /**
     * The user's password.
     */
    protected final String password;

    public RegisterRequest(String firstName, String lastName, String image, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
        this.username = username;
        this.password = password;
    }
}

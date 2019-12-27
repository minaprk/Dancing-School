import java.io.Serializable;

public class User implements Serializable {
    private Long id;
    private String name;
    private String login;
    private String password;
    private String email;
    private String phoneNumber;
    private int mem_count;

    public User() {}


    public User(Long id, String name, String login, String password, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public User(Long id, String name, String login, String password, String email, String phoneNumber, int mem_count) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.mem_count = mem_count;
    }

    public int getMem_count() {
        return mem_count;
    }

    public void setMem_count(int mem_count) {
        this.mem_count = mem_count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public boolean signIn(String login, String password){
        return login.equals(this.login) && password.equals(this.password);
    }

}

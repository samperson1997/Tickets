package tickets.bean;

public class UserBean {
    private String email;

    private String name;

    private String password;

    private int isMember;

    private String level;

    private int score;

    private String pin;

    private double account;

    public UserBean() {
    }

    public UserBean(String email, String name, String password, int isMember, String level, int score, String pin, double account) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.isMember = isMember;
        this.level = level;
        this.score = score;
        this.pin = pin;
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsMember() {
        return isMember;
    }

    public void setIsMember(int isMember) {
        this.isMember = isMember;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
        this.account = account;
    }
}

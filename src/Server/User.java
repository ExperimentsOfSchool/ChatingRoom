package Server;

/**
 * Created by Lawrence on 3/23/16.
 *
 */
public class User {
    private String userName;
    private String passWord;
    private boolean loginStatus;
    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
        this.loginStatus = false;
    }
    public boolean userAuth(String passWord) {
        if(this.passWord.equals(passWord)) {
            this.loginStatus = true;
            return true;
        } else {
            this.loginStatus = false;
            return false;
        }
    }
    public boolean getLoginStatus() {
        return loginStatus;
    }
    public String getUserName() {
        return userName;
    }
    public void logout() {
        this.loginStatus = false;
    }
}

package Server;

import java.util.ArrayList;

/**
 * Created by Lawrence on 3/23/16.
 *
 */
public class UserList {
    private static UserList userList = new UserList();
    private ArrayList<User> users;
    private UserList() {
        users = new ArrayList<>();
        this.initUserList();
    }
    public static UserList getInstance() {
        return userList;
    }
    private void initUserList() {
        this.users.add(new User("Tom", "123456"));
        this.users.add(new User("Lawrence", "123456"));
        this.users.add(new User("Qwerty", "123456"));
        this.users.add(new User("Hello", "123456"));
    }
    public boolean addUser(User newUser) {
        for(User user: users) {
            if(user.getUserName().equals(newUser.getUserName())) {
                return false;
            }
        }
        users.add(newUser);
        return true;
    }
    public int authUser(String name, String password) {
        for(User user: users) {
            if(user.getUserName().equals(name)) {
                if(user.getLoginStatus()) {
                    return 1; //Already login.
                } else {
                    if(user.userAuth(password)) {
                        return 0; //login success.
                    } else {
                        return 2; //Wrong password.
                    }
                }
            }
        }
        return 3; //No such user.
    }
    public String getUserList(String applier) {
        String list = "";
        for(User user: users) {
            if(!user.getUserName().equals(applier)) {
                list += user.getUserName() + ",";
            }
        }
        if(!list.equals("")) {
            return list.substring(0, list.length() - 1);
        } else {
            return "";
        }
    }
    public void userLogout(String userName) {
        for(User user: users) {
            if(user.getUserName().equals(userName)) {
                if(user.getLoginStatus()) {
                    user.logout();
                }

            }
        }
        System.out.println("Logged out!");
    }
    public boolean getUserStatus(String userName) {
        for(User user: users) {
            if(user.getUserName().equals(userName)) {
                return user.getLoginStatus();
            }
        }
        return false;
    }

}

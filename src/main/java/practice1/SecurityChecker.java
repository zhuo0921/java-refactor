package practice1;

public class SecurityChecker {
    public  boolean checkPermission(User user, Permission permission) {
        boolean flag = true;
        if(user == null || permission == null ) flag = false;
        return flag;
    }

    public boolean isAdmin() {
        return false;
    }
}

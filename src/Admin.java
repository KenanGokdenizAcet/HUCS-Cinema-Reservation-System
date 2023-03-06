public class Admin extends User{
    public Admin(String username, String password, boolean clubMember) {
        super(username, password, clubMember);
        this.userType = true;
    }

    public String getStatus() {
        if (clubMember) {
            return ("(Admin - Club Member)");
        } else {
            return ("(Admin)");
        }
    }
}

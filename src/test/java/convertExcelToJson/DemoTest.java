package convertExcelToJson;

public class DemoTest {

    private int tcid;
    private String url;
    private String username;
    private String pass;
    private String passfail;

    public DemoTest(int tcid, String url, String username, String pass, String passfail) {
        this.tcid = tcid;
        this.url = url;
        this.username = username;
        this.pass = pass;
        this.passfail = passfail;
    }

    public int getTcid() {
        return tcid;
    }

    public void setTcid(int tcid) {
        this.tcid = tcid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPassfail() {
        return passfail;
    }

    public void setPassfail(String passfail) {
        this.passfail = passfail;
    }
}

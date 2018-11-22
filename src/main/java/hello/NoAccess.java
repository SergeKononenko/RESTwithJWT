package hello;

public class NoAccess {

   // private final long id;
    private final String content = "No Access";

    public String getContent() {
        return content;
    }
}
package model;

import java.sql.Timestamp;

public class Comment {
    private final int id;
    private String body;
    private Timestamp time;

    public Comment(int id, String body, Timestamp time){
        this.id = id;
        this.body = body;
        this.time = time;
    }

    public int getCommentId() { return this.id; }
    public String getCommentBody() { return this.body; }
    public String getCommentOrigin() { return this.time.toString(); }
}

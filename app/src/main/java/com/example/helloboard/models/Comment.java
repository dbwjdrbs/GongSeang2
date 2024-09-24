package com.example.helloboard.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Comment {

    private String documentId;
    private String nickname;
    private String contents;


    @ServerTimestamp
    private Date date;

    public Comment() {
    }

    public Comment(String nickname, String contents, String documentId) {
        this.documentId = documentId;
        this.nickname = nickname;
        this.contents = contents;
    }

    public String getDocumentId() { return documentId; }

    public void setDocumentId(String documentId) { this.documentId = documentId; }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "documentId='" + documentId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", contents='" + contents + '\'' +
                ", date=" + date +
                '}';
    }
}
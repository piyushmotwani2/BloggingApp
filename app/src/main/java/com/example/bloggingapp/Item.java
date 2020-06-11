package com.example.bloggingapp;

public class Item {
    String cardBackground;
    String articleName;
    String authorName;
    String numoflikes;
    String [] tags;

    public Item() {
    }

    public Item(String cardBackground, String articleName, String authorName, String numoflikes, String[] tags) {
        this.cardBackground = cardBackground;
        this.articleName = articleName;
        this.authorName = authorName;
        this.numoflikes = numoflikes;
        this.tags = tags;
    }

    public String getCardBackground() {
        return cardBackground;
    }

    public String getArticleName() {
        return articleName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getNumoflikes() {
        return numoflikes;
    }

    public String [] getTags() { return tags; }


    public void setCardBackground(String background) {
        this.cardBackground = cardBackground;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setNumoflikes(String numoflikes) {
        this.numoflikes = numoflikes;
    }

    public void setTags(String [] tags) { this.tags = tags; }

}

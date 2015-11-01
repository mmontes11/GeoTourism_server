package com.mmontes.rest.response;

public class WikipediaResult {
    private String title;
    private String url;

    public WikipediaResult(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public WikipediaResult() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "WikipediaResult{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

package com.victor.ray.event.beans;

public class  MediaDetail {
    private String externalApiId;
    private String url;
    private String description;
    private String tinyUrl;

    public String getExternalApiId() {
        return externalApiId;
    }

    public void setExternalApiId(String externalApiId) {
        this.externalApiId = externalApiId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTinyUrl() {
        return tinyUrl;
    }

    public void setTinyUrl(String tinyUrl) {
        this.tinyUrl = tinyUrl;
    }
}

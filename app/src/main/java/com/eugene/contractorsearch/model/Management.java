package com.eugene.contractorsearch.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Management {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("post")
    @Expose
    private String post;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}

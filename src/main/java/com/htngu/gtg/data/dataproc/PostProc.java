package com.htngu.gtg.data.dataproc;

import lombok.Data;

import java.util.Map;


@Data
public class PostProc {

    private int id;
    private String userName;
    private String firstName;
    private String lastName;
    private String avatar;
    private String status;
    private String createAt;
    private String updateAt;
    private String content;
    private String postType;
    private String cost;
    private String images;

    public void setData(Map map){
        System.out.println("==============="+map.toString());
        id = (int) map.get("id");
        userName = (String) map.get("username");
        firstName = (String) map.get("first_name");
        lastName = (String) map.get("last_name");
        avatar = (String) map.get("avatar");
        status = (String) map.get("status");
        createAt = (String) map.get("create_at");
        updateAt = (String) map.get("update_at");
        content = (String) map.get("content");
        postType = (String) map.get("post_type");
        cost = (String) map.get("cost");
        images = (String) map.get("images");
    }
}

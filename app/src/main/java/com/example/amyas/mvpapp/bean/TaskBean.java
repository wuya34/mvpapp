package com.example.amyas.mvpapp.bean;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * author: Amyas
 * date: 2017/12/26
 */
@Entity
public class TaskBean {
    @Id
    private long id;
    private String title;
    private String detail;

    public TaskBean(String title, String detail) {
        this.title = title;

        this.detail = detail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TaskBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

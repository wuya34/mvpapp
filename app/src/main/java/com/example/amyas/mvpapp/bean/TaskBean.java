package com.example.amyas.mvpapp.bean;

import com.example.amyas.mvpapp.util.Prediction;

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
    private boolean completed;



    @Override
    public String toString() {
        return "TaskBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", completed=" + completed +
                '}';
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isEmpty() {
        return Prediction.isNullOrEmpty(title) &&
                Prediction.isNullOrEmpty(detail);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

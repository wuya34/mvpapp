package com.example.amyas.mvpapp.bean;

import com.example.amyas.mvpapp.util.BeanUtil;

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

    public TaskBean(String title, String detail, boolean completed) {
        this.title = title;
        this.detail = detail;
        this.completed = completed;
    }

    public TaskBean(String title, String detail) {
        this(title, detail, false);
    }

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
        return BeanUtil.isNullOrEmpty(title) &&
                BeanUtil.isNullOrEmpty(detail);
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

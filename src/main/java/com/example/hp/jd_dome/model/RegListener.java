package com.example.hp.jd_dome.model;

/**
 * Created by a on 2018/5/8.
 */

public interface RegListener {
    void regSuccess(String json);

    void regError(String error);
}

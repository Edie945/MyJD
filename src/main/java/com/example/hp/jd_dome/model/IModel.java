package com.example.hp.jd_dome.model;

import java.util.Map;

/**
 * Created by a on 2018/5/8.
 */

public interface IModel {
     void getLoginDatas(String url, Map<String, String> map, LoginListener loginListener);

     void getRegDatas(String url, Map<String, String> map, RegListener regListener);
}

package com.example.hp.jd_dome.model;

import com.example.hp.jd_dome.model.bean.LoginBean;
import com.example.hp.jd_dome.model.bean.RegBean;
import com.example.hp.jd_dome.model.http.HttpUtils;
import com.example.hp.jd_dome.model.http.OkLoadListener;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by a on 2018/5/8.
 */

public class ModelImpel implements IModel{
    @Override
    public void getLoginDatas(String url, Map<String, String> map, final LoginListener loginListener) {
        HttpUtils httpUtils = HttpUtils.getHttpUtils ();

        httpUtils.okPost (url,map);

        httpUtils.setOkLoadListener (new OkLoadListener() {
            @Override
            public void okLoadSuccess(String json) {
                Gson gson = new Gson();

                LoginBean loginBean = gson.fromJson (json, LoginBean.class);

                if(loginBean.getCode ().equals ("0")){
                    loginListener.loginSuccess (json);
                }else{
                    loginListener.loginError (json);
                }
            }

            @Override
            public void okLoadError(String error) {
                loginListener.loginError (error);
            }
        });
    }

    @Override
    public void getRegDatas(String url, Map<String, String> map, final RegListener regListener) {
        HttpUtils httpUtils = HttpUtils.getHttpUtils ();

        httpUtils.okPost (url,map);

        httpUtils.setOkLoadListener (new OkLoadListener () {
            @Override
            public void okLoadSuccess(String json) {
                Gson gson = new Gson();

                RegBean regBean = gson.fromJson (json, RegBean.class);

                if(regBean.getCode ().equals ("0")){
                    regListener.regSuccess (json);
                }else{
                    regListener.regError (json);
                }
            }

            @Override
            public void okLoadError(String error) {
                regListener.regError (error);
            }
        });
    }
}

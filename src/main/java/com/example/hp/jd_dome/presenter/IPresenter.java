package com.example.hp.jd_dome.presenter;


import com.example.hp.jd_dome.model.IModel;
import com.example.hp.jd_dome.view.interfaces.ILoginView;
import com.example.hp.jd_dome.view.interfaces.IRegView;

/**
 * Created by a on 2018/5/8.
 */

public interface IPresenter {
    void ShowLoginToView(IModel iModel, ILoginView iLoginView);

    void ShowRegToView(IModel iModel, IRegView iRegView);
}

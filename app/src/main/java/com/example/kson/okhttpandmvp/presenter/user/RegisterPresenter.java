package com.example.kson.okhttpandmvp.presenter.user;

import android.text.TextUtils;

import com.example.kson.okhttpandmvp.bean.UserBean;
import com.example.kson.okhttpandmvp.model.user.RegisterModel;
import com.example.kson.okhttpandmvp.utils.RegexValidateUtil;
import com.example.kson.okhttpandmvp.view.IRegView;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/08/07
 * Description:
 */
public class RegisterPresenter {

    private RegisterModel registerModel;
    private IRegView iRegView;

    public RegisterPresenter(IRegView iRegView) {
        this.iRegView = iRegView;
        this.registerModel = new RegisterModel();
    }

    /**
     * 注册逻辑
     * @param mobile
     * @param pwd
     */
    public void register(String mobile, String pwd) {
        if (TextUtils.isEmpty(mobile)){

            iRegView.mobileEmpty();

            return;
        }

        if (!RegexValidateUtil.checkCellphone(mobile)){
            iRegView.mobileVerify();
            return;
        }
        if (TextUtils.isEmpty(pwd)){
            iRegView.pwdVerify();
            return;
        }

        registerModel.register(mobile, pwd, new RegisterModel.RegCallback() {
            @Override
            public void failure(String errorMsg) {

                iRegView.failure(errorMsg);

            }

            @Override
            public void success(UserBean userBean) {

                iRegView.success(userBean);

            }
        });


    }
}

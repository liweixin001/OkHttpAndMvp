package com.example.kson.okhttpandmvp.view;

import com.example.kson.okhttpandmvp.bean.UserBean;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/08/07
 * Description:
 */
public interface IRegView {

    void mobileVerify();//校验手机号合法
    void mobileEmpty();
    void pwdVerify();//校验密码
    void success(UserBean userBean);//请求成功
    void failure(String msg);//请求失败

}

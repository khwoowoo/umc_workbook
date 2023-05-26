package umc.UMC_WORKBOOK.controller;

import umc.UMC_WORKBOOK.domain.User;

public class LoginUser {
    private static User loginUser;
    private static LoginUser instance;
    public LoginUser(){
        loginUser = null;
    }

    public static LoginUser getInstance(){
        if (instance == null){
            instance = new LoginUser();
        }

        return instance;
    }

    public static boolean isLoginUser(){
        return (loginUser != null);
    }


    public static void  setInstance(User user){
        loginUser = user;
    }

    public static User GetLoginUser(){
        return  loginUser;
    }

}

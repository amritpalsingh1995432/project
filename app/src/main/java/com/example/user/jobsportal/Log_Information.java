package com.example.user.jobsportal;

public class Log_Information {

        private static String logged_in_user;
        private static String userType;

        public void setLogged_in_user(String userId)
        {
            logged_in_user = userId;
        }

        public String getLogged_in_user()
        {
            return logged_in_user;
        }

        public void setUserType(String type){
            userType = type;
        }

        public String getUserType(){
            return userType;
        }

}

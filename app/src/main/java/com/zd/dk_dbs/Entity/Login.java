package com.zd.dk_dbs.Entity;

/**
 * Created by Administrator on 2017/3/14 0014.
 */

public class Login {
    /**
     * result : {"created_at":1489481555937,"updated_at":1489481555989,"id":1165009125179394,"user_data":{"phone":"18336633313","user_name":"赵迪","avatar":"123","sign":"123"}}
     * error_code : 0
     */

    public ResultBean result;
    public int error_code;


    public static class ResultBean {
        /**
         * created_at : 1489481555937
         * updated_at : 1489481555989
         * id : 1165009125179394
         * user_data : {"phone":"18336633313","user_name":"赵迪","avatar":"123","sign":"123"}
         */

        public long created_at;
        public long updated_at;
        public long id;
        public UserDataBean user_data;

        public static class UserDataBean {
            /**
             * phone : 18336633313
             * user_name : 赵迪
             * avatar : 123
             * sign : 123
             */

            public String phone;
            public String user_name;
            public String avatar;
            public String sign;

        }
    }
}

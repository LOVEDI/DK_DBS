package com.zd.dk_dbs.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class LiveIng implements Serializable{


    /**
     * result : {"list":[{"created_at":1489060085463,"updated_at":1489060963928,"id":1157938031951875,"data":{"status":0,"live_name":"马泽宇直播","pic":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489070127924&di=1265b69c4092d0f9c4be4095eead6021&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fca1349540923dd54261fa262d309b3de9c82484f.jpg","live_type":0},"uid":1157677280460801,"user":{"user_data":{"user_name":"小源","avatar":"http://d.hiphotos.baidu.com/zhidao/wh%3D600%2C800/sign=44efbe491e30e924cff194377c38423e/dcc451da81cb39dbf51ac417d1160924aa18309c.jpg"},"id":1157677280460801,"created_at":1489044543163,"updated_at":1489044543225}},{"created_at":1489060118617,"updated_at":1489060972136,"id":1157938585600004,"data":{"status":0,"live_name":"杨文静直播","pic":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489070127921&di=b7193a4b0f083fb5e54310e4f2836f4f&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fdbb44aed2e738bd4770bcc30a38b87d6277ff98d.jpg","live_type":0},"uid":1157677280460801,"user":{"user_data":{"user_name":"小源","avatar":"http://d.hiphotos.baidu.com/zhidao/wh%3D600%2C800/sign=44efbe491e30e924cff194377c38423e/dcc451da81cb39dbf51ac417d1160924aa18309c.jpg"},"id":1157677280460801,"created_at":1489044543163,"updated_at":1489044543225}},{"created_at":1489060143902,"updated_at":1489060981240,"id":1157939005030405,"data":{"status":0,"live_name":"边翠霞直播","pic":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489070127919&di=703d167b664bd5fb15951419addb8464&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F7c1ed21b0ef41bd5254b96f553da81cb39db3d92.jpg","live_type":0},"uid":1157677280460801,"user":{"user_data":{"user_name":"小源","avatar":"http://d.hiphotos.baidu.com/zhidao/wh%3D600%2C800/sign=44efbe491e30e924cff194377c38423e/dcc451da81cb39dbf51ac417d1160924aa18309c.jpg"},"id":1157677280460801,"created_at":1489044543163,"updated_at":1489044543225}}]}
     * error_code : 0
     */

    public ResultBean result;
    public int error_code;


    public static class ResultBean implements Serializable{
        public List<ListBean> list;
        public static class ListBean implements Serializable{
            /**
             * created_at : 1489060085463
             * updated_at : 1489060963928
             * id : 1157938031951875
             * data : {"status":0,"live_name":"马泽宇直播","pic":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489070127924&di=1265b69c4092d0f9c4be4095eead6021&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fca1349540923dd54261fa262d309b3de9c82484f.jpg","live_type":0}
             * uid : 1157677280460801
             * user : {"user_data":{"user_name":"小源","avatar":"http://d.hiphotos.baidu.com/zhidao/wh%3D600%2C800/sign=44efbe491e30e924cff194377c38423e/dcc451da81cb39dbf51ac417d1160924aa18309c.jpg"},"id":1157677280460801,"created_at":1489044543163,"updated_at":1489044543225}
             */

            public long created_at;
            public long updated_at;
            public long id;
            public DataBean data;
            public long uid;
            public UserBean user;



            public static class DataBean implements Serializable{
                /**
                 * status : 0
                 * live_name : 马泽宇直播
                 * pic : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489070127924&di=1265b69c4092d0f9c4be4095eead6021&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fca1349540923dd54261fa262d309b3de9c82484f.jpg
                 * live_type : 0
                 */

                public int status;
                public String live_name;
                public String pic;
                public int live_type;

            }

            public static class UserBean implements Serializable{
                /**
                 * user_data : {"user_name":"小源","avatar":"http://d.hiphotos.baidu.com/zhidao/wh%3D600%2C800/sign=44efbe491e30e924cff194377c38423e/dcc451da81cb39dbf51ac417d1160924aa18309c.jpg"}
                 * id : 1157677280460801
                 * created_at : 1489044543163
                 * updated_at : 1489044543225
                 */

                public UserDataBean user_data;
                public long id;
                public long created_at;
                public long updated_at;

                public static class UserDataBean implements Serializable{
                    /**
                     * user_name : 小源
                     * avatar : http://d.hiphotos.baidu.com/zhidao/wh%3D600%2C800/sign=44efbe491e30e924cff194377c38423e/dcc451da81cb39dbf51ac417d1160924aa18309c.jpg
                     */

                    public String user_name;
                    public String avatar;

                }
            }
        }
    }
}

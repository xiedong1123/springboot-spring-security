package com.ccl.base.utils.qiniu;

import com.qiniu.util.Auth;

/**
 * @author ：Yuan Fayang
 * @email : fayang.yuan@changhong.com
 * @package ; com.changhong.open.man.web.base.utils.qiniu
 * @dateTime : 2015/12/21 8:52
 * @discription : 七牛云存上传相关的配置
 */
public class QiniuConfig {

    public static String ACCESS_KEY = null;
    static {
        ACCESS_KEY = ReadPropertiesUtils.read("qiniu.ACCESS_KEY");
    }
    public static String SECRET_KEY = null;
    static {
        SECRET_KEY = ReadPropertiesUtils.read("qiniu.SECRET_KEY");
    }
    public static String BUCKET = null;
    static {
        BUCKET = ReadPropertiesUtils.read("qiniu.BUCKET");
    }
    public static String DOWNLOAD_DOMAIN = null;
    static {
        DOWNLOAD_DOMAIN = ReadPropertiesUtils.read("qiniu.DOWNLOAD_DOMAIN");
    }
    public static String RETURN_URL = null;
    static {
        RETURN_URL = ReadPropertiesUtils.read("qiniu.RETURN_URL");
    }
    public static Auth AUTH = null;
    static {
        AUTH = Auth.create(ACCESS_KEY,SECRET_KEY);
    }

    public QiniuConfig() {
    }

    public static boolean isTravis(){
        return "travis".equals(System.getenv("QINIU_TEST_ENV"));
    }
}

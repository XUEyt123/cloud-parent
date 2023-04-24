package org.example.cloud.comment.utils;

import com.baidu.aip.contentcensor.AipContentCensor;
import com.baidu.aip.contentcensor.EImgType;
import org.json.JSONObject;

public class BaiduContentCensor {
    // 应用ID
    public static final String APP_ID = "17540802";
    public static final String API_KEY = "wKE4Iuyd383Wld3GXCKCGNF7";
    public static final String SECRET_KEY = "cUDtMt9n5Ur4xoQRKiLizW5flGLS15AZ";

    private AipContentCensor client;

    private BaiduContentCensor() {
        client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
    }

    private static class BaiduContentSingle {
        public static BaiduContentCensor contentCensor = new BaiduContentCensor();
    }

    public static BaiduContentCensor getInstance() {
        return BaiduContentSingle.contentCensor;
    }

    /**
     * 文本内容审核
     */
    public boolean censorText(String msg) {
        JSONObject response = client.textCensorUserDefined(msg);
        System.out.println(response.toString(2));
        int r = response.getInt("conclusionType");
        return r == 1;
    }

    /**
     * 图片审核
     */
    public boolean censorImg(byte[] data) {
        JSONObject response = client.imageCensorUserDefined(data, null);
        int r = response.getInt("conclusionType");
        return r == 1;
    }

    /**
     * 网络图片审核
     */
    public boolean censorImg(String imgPath) {
        JSONObject response = client.imageCensorUserDefined(imgPath, EImgType.URL, null);
        int r = response.getInt("conclusionType");
        return r == 1;
    }


}

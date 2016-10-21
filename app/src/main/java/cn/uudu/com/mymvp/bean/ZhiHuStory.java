package cn.uudu.com.mymvp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/10/21.
 */
public class ZhiHuStory {

    @SerializedName("body")
    public String body;
    @SerializedName("title")
    public String title;
    @SerializedName("image")
    public String image;
    @SerializedName("share_url")
    public String mShareUrl;
    @SerializedName("css")
    public String[] css;
}

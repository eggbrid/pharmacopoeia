package com.pharmacopoeia.bean.model;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * Created by 52243 on 2017/4/11.
 */

public class WebBeanModel implements Serializable {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private long u_id;
    private String title;
    private String url;
    private String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

package com.pharmacopoeia.bean.model;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * Created by xus on 2017/9/7.
 */
@Table("tb_shop_type")
public class ShopType implements Serializable {
    @PrimaryKey(AssignType.BY_MYSELF)
    private String cateId;// 6,
    private String cateName;//美颜
    private String createTime;//2017-08-21 22:23:23
    private String lastTime;//2017-08-21 22:23:23"

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}

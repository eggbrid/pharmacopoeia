package com.pharmacopoeia.bean.cache;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by xus on 2017/9/15.
 */
@Table("tb_tk_address")

public class TKAddress {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int aid ;
    private String name;//北京同仁堂连锁药店有限责任公司西站北广场药店",
    private String phone;//63941471（传真）",
    private String address;//北京市海淀区莲花池东路31号中裕世纪大酒店一层"

    private boolean isVis=false;

    public boolean isVis() {
        return isVis;
    }

    public void setVis(boolean vis) {
        isVis = vis;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

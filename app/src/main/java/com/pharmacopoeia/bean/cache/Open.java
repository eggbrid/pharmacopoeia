package com.pharmacopoeia.bean.cache;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by xus on 2017/7/13.
 */
@Table("tb_open")
public class Open {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id=1;
    private String fileUrl;//0为第一次 1为多次

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}

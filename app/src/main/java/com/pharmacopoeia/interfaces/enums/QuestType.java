package com.pharmacopoeia.interfaces.enums;

import com.pharmacopoeia.R;

/**
 * Created by xus on 2017/8/30.
 */

public enum QuestType {
    RADIOQ1(R.layout.recuperate_quest_radio_one_item, "R1"),RADIOQ2(R.layout.recuperate_quest_radio_two_item, "R2"),RADIOQ3(R.layout.recuperate_quest_radio_three_item, "R3"),RADIOQ4(R.layout.recuperate_quest_radio_four_item, "R4")
    ,CHECKBOXQ1(R.layout.recuperate_quest_checkbox_one_item, "C1") ,CHECKBOXQ2(R.layout.recuperate_quest_checkbox_two_item, "C2") ,CHECKBOXQ3(R.layout.recuperate_quest_checkbox_three_item, "C3"),CHECKBOXQ4(R.layout.recuperate_quest_checkbox_four_item, "C4"),FINISH(R.layout.recuperate_quest_finish_item, "F");
    private int layout;
    private String type;

    QuestType(int layout, String type) {
        this.layout = layout;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getLayout() {
        return this.layout;
    }

    public static QuestType getEnum(String value) {
        QuestType resultEnum = null;
        QuestType[] enumAry = QuestType.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getType().equals(value)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

    public static QuestType getEnum(String type,int size) {
        String value="";
        if ("1".equals(type)){
            value="C"+size;
        }else{
            value="R"+size;
        }
        return getEnum(value);
    }
}
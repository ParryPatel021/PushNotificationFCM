package com.pushnotificationfcm.ws.models;

import java.io.Serializable;

public class CommonViewInfo implements Serializable {

    private String item_head;
    private Object item_val;
    public CommonViewInfo() {

    }

    public CommonViewInfo(String item_head, Object item_val) {
        this.item_head = item_head;
        this.item_val = item_val;
    }

    public String getItem_head() {
        return item_head;
    }

    public void setItem_head(String item_head) {
        this.item_head = item_head;
    }


    public Object getItem_val() {
        return item_val;
    }

    public void setItem_val(Object item_val) {
        this.item_val = item_val;
    }

    public Object getItem_array() {
        return item_val;
    }
}

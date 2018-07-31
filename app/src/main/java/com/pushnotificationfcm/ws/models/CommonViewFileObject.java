package com.pushnotificationfcm.ws.models;

import java.io.Serializable;

public class CommonViewFileObject implements Serializable {

    private String file_path;
    private long file_id;
    public CommonViewFileObject() {

    }

    public CommonViewFileObject(String file_path, long file_id) {
        this.file_path = file_path;
        this.file_id = file_id;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public long getFile_id() {
        return file_id;
    }

    public void setFile_id(long file_id) {
        this.file_id = file_id;
    }
}

package com.example.myapplication.model;

import java.util.Map;

public class DjangoResponse {
    private String model;
    private String pk;
    private Object fields;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public Object getFields() {
        return fields;
    }

    public void setFields(Object fields) {
        this.fields = fields;
    }
}

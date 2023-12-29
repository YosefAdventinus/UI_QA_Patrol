package com.example.ui_qa_patrol.models;

public class Config {

    private long id;
    private String tvar, tval, updated_at;

    public Config(){}

    public Config(long id, String tvar, String tval, String updated_at){
        this.id = id;
        this.tvar = tvar;
        this.tval = tval;
        this.updated_at = updated_at;
    }

    public long getId() {
        return id;
    }
    public void setId(long id){
        this.id = id;
    }
    public String getTvar(){
        return tvar;
    }
    public void setTvar(String tvar){
        this.tvar = tvar;
    }
    public String getTval(){
        return tval;
    }
    public void setTval(String tval){
        this.tval = tval;
    }
    public String getUpdated_at(){
        return updated_at;
    }
    public void setUpdated_at(String updated_at){
        this.updated_at = updated_at;
    }


}

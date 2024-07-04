package com.server.server.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "data")
public class DataModel {
    @Id
    private String id;

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;
    private String appName;

    public String getEmail() {
        return email;
    }

    private String appPassword;

    @Override
    public String toString() {
        return "DataModel [Id=" + id + ", email=" + email + ", appName=" + appName + ", appPassword=" + appPassword
                + ", lastUpdate=" + lastUpdate + "]";
    }

    public String getId() {
        return id;
    }

    public String getAppName() {
        return appName;
    }

    public void setId(String Id) {
        id = Id;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAppPassword(String appPassword) {
        this.appPassword = appPassword;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getAppPassword() {
        return appPassword;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    private Date lastUpdate;

}

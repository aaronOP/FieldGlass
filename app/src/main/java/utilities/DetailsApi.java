package utilities;

import android.app.Application;
//accessible across application

public class DetailsApi extends Application {
    private String username;
    private String userId;
    private static DetailsApi instance;

    public static DetailsApi getInstance() {
        if (instance == null)
            instance = new DetailsApi();
        return instance;
    }


    public DetailsApi(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String currentUserId) {
        this.userId = userId;
    }
}

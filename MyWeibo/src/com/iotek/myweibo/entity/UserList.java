package com.iotek.myweibo.entity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.sina.weibo.sdk.openapi.models.User;

public class UserList {
	/** 用户列表 */
    public ArrayList<User> UserList;
    public User user;
    /** 字符串型的用户 UID */
    public String idstr;
    /** 用户昵称 */
    public String screen_name;
    /** 用户头像地址，50×50像素 */
    public String profile_image_url;
    /** 用户个人描述 */
    public String description;
    
    public static UserList parse(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        
       UserList users = new UserList();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            
            JSONArray jsonArray      = jsonObject.optJSONArray("users");
            if (jsonArray != null && jsonArray.length() > 0) {
                int length = jsonArray.length();
                users.UserList = new ArrayList<User>(length);
                for (int ix = 0; ix < length; ix++) {
                    users.UserList.add(User.parse(jsonArray.getJSONObject(ix)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return users;
    }
}

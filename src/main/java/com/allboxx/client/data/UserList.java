package com.allboxx.client.data;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import java.util.List;

/**
 * User: mtolstykh
 * Date: 04.08.14
 * Time: 15:54
 */
@JsonAutoDetect
public class UserList {
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

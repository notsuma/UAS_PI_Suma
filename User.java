package microservices;
import java.util.*;

class User {
    String userId;
    String userName;
    Map<String, Integer> stocks;
    Map<String, Integer> bonds;

    User(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.stocks = new HashMap<>();
        this.bonds = new HashMap<>();
    }
}

class UserService {
    Map<String, User> users;

    UserService() {
        this.users = new HashMap<>();
    }

    void addUser(String userId, String userName) {
        User user = new User(userId, userName);
        users.put(userId, user);
    }

    void removeUser(String userId) {
        users.remove(userId);
    }

    User getUser(String userId) {
        return users.get(userId);
    }
}

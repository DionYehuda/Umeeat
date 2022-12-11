package id.ac.umn.umeeat;

import java.util.List;

//Masih WIP
public class Chat {
    public User friend;
    public List<String> chatContents;

    Chat(){}

    Chat(User friend, List<String> chatContents)
    {
        this.friend = friend;
        this.chatContents = chatContents;
    }
}

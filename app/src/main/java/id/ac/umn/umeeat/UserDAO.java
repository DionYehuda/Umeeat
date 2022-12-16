package id.ac.umn.umeeat;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class UserDAO {
    private DatabaseReference dataRef, chatRef;

    public UserDAO() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dataRef = db.getReference("User");
        chatRef = db.getReference("Chat");
    }

    public Task<Void> add (User ur, String uid)
    {
        DatabaseReference newUser;
        if (ur != null)
        {
            newUser = dataRef.child(uid);
            return newUser.setValue(ur);
        }
        else
            return null;
    }

    public void updateDesc(String username, String desc)
    {
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren())
                {
                    User user = snap.getValue(User.class);
                    if(username.equalsIgnoreCase(user.getUname()))
                    {
                        snap.getRef().child("desc").setValue(desc);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void checkIfUnique(String username, FoundCallback myCallback)
    {
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren())
                {
                    User user = snap.getValue(User.class);
                    if(username.equalsIgnoreCase(user.getUname()))
                    {
                        myCallback.onCallback(false);
                        return;
                    }
                }
                myCallback.onCallback(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loginIterate(String username, String pass, UserCallback myCallback)
    {
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren())
                {
                    User user = snap.getValue(User.class);
                    if(username.equalsIgnoreCase(user.getUname()) && pass.equals(user.getPass()))
                    {
                        myCallback.onCallback(user);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void userIterate(String username, UserCallback myCallback)
    {
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren())
                {
                    User user = snap.getValue(User.class);
                    if(username.equalsIgnoreCase(user.getUname()))
                    {
                        myCallback.onCallback(user);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void chatIterate(String username, UserCallback myUserCallback)
    {
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HomeActivity.friends.clear();
                HomeActivity.lastChat.clear();
                for (DataSnapshot snap: snapshot.getChildren())
                {
                    String chatRoom = snap.getKey();
                    if(chatRoom.contains(username+"&")||chatRoom.contains("&"+username))
                    {
                        String[] temp = chatRoom.split("&");
                        List<String> chatMembers = Arrays.asList(temp);
                        String chatPartner;
                        for (int i = 0; i<chatMembers.size(); i++)
                        {
                            if(!chatMembers.get(i).equalsIgnoreCase(username))
                            {
                                chatPartner = chatMembers.get(i);
                                userIterate(chatPartner, myUserCallback);
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getLastChats(String username, String friendname, ChatCallback myChatCallback)
    {
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren())
                {
                    String chatRoom = snap.getKey();
                    if(chatRoom.equals(username+"&"+friendname) || chatRoom.equals(friendname+"&"+username))
                    {
                        String[] temp = chatRoom.split("&");
                        List<String> chatMembers = Arrays.asList(temp);
                        for (int i = 0; i<chatMembers.size(); i++)
                        {
                            if(!chatMembers.get(i).equalsIgnoreCase(username))
                            {
                                Query lastQuery = snap.getRef().orderByKey().limitToLast(1);
                                lastQuery.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot data : snapshot.getChildren())
                                        {
                                            String[] arrofstr = data.getValue(String.class).split(":", 3);
                                            String lastChat;
                                            if (arrofstr[1].equals("Text"))
                                            {
                                                lastChat = arrofstr[2];
                                                myChatCallback.onCallback(lastChat);
                                            }
                                            else if(arrofstr[1].equals("Maps"))
                                            {
                                                if(arrofstr[0].equals(username))
                                                {
                                                    lastChat = "Your Location";
                                                    myChatCallback.onCallback(lastChat);
                                                }
                                                else if(arrofstr[0].equals(friendname))
                                                {
                                                    lastChat = friendname + "'s Location";
                                                    myChatCallback.onCallback(lastChat);
                                                }
                                            }
                                            else if(arrofstr[1].equals("Foto"))
                                            {
                                                if(arrofstr[0].equals(username))
                                                {
                                                    lastChat = "You sent a Photo";
                                                    myChatCallback.onCallback(lastChat);
                                                }
                                                else if(arrofstr[0].equals(friendname))
                                                {
                                                    lastChat = friendname + " sent a photo";
                                                    myChatCallback.onCallback(lastChat);
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getAllUsers(String username, UserCallback myCallback) {
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SearchActivity.items.clear();
                for (DataSnapshot snap: snapshot.getChildren())
                {
                    User user = snap.getValue(User.class);
                    if(!(username.equalsIgnoreCase(user.getUname())))
                    {
                        myCallback.onCallback(user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void searchUserByAngkatan(String username, UserCallback myCallback, String kueri){
        Query query = dataRef.orderByChild("year").equalTo(kueri);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SearchActivity.items.clear();
                for (DataSnapshot snap: snapshot.getChildren())
                {
                    User user = snap.getValue(User.class);
                    if(!(username.equalsIgnoreCase(user.getUname())))
                    {
                        myCallback.onCallback(user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                SearchActivity.items.clear();
            }
        });
    }

    public void searchUserByJurusan(String username, UserCallback myCallback, String kueri){
        Query query = dataRef.orderByChild("jurusan").equalTo(kueri);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SearchActivity.items.clear();
                for (DataSnapshot snap: snapshot.getChildren())
                {
                    User user = snap.getValue(User.class);
                    if(!(username.equalsIgnoreCase(user.getUname())))
                    {
                        myCallback.onCallback(user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                SearchActivity.items.clear();

            }
        });
    }

    public void searchUserByGender(String username, UserCallback myCallback, String kueri){
        Query query = dataRef.orderByChild("gender").equalTo(kueri);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SearchActivity.items.clear();
                for (DataSnapshot snap: snapshot.getChildren())
                {
                    User user = snap.getValue(User.class);
                    if(!(username.equalsIgnoreCase(user.getUname())))
                    {
                        myCallback.onCallback(user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                SearchActivity.items.clear();

            }
        });
    }
}

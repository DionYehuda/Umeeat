package id.ac.umn.umeeat;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

//    ga perlu async maybe
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

    public void loginIterate(String username, String pass, UserCallback myCallback)
    {
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren())
                {
                    User user = snap.getValue(User.class);
                    if(username.equals(user.getUname()) && pass.equals(user.getPass()))
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
                    if(username.equals(user.getUname()))
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

    public void chatIterate(String username, UserCallback myCallback)
    {
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HomeActivity.friends.clear();
                for (DataSnapshot snap: snapshot.getChildren())
                {
                    String chatRoom = snap.getKey();
                    if(chatRoom.contains(username))
                    {
                        String[] temp = chatRoom.split("&");
                        List<String> chatMembers = Arrays.asList(temp);
                        String chatPartner;
                        for (int i = 0; i<chatMembers.size(); i++)
                        {
                            if(!chatMembers.get(i).equals(username))
                            {
                                chatPartner = chatMembers.get(i);
                                userIterate(chatPartner, myCallback);
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
                    if(!(username.equals(user.getUname())))
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
}
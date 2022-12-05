package id.ac.umn.umeeat;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDAO {
    private DatabaseReference dataRef;

    public UserDAO() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dataRef = db.getReference("User");
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

    public void loginIterate(String username, String pass, MyCallback myCallback)
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
}
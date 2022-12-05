package id.ac.umn.umeeat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDAO {
    private DatabaseReference dataRef;

    public UserDAO() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dataRef = db.getReference("User");
    }

    public Task<Void> add (User ur)
    {
        if (ur != null)
            return dataRef.setValue(ur);
        else
            return null;
    }
}

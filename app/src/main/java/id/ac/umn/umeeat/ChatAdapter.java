package id.ac.umn.umeeat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatItemHolder> {

    private final LinkedList<User> friends;
    private final LinkedList<String> lastChat;
    private final LayoutInflater inflater;
    Context context;
    User me;

    public ChatAdapter(Context context, LinkedList<User> friends, LinkedList<String> lastChat, User me)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.friends = friends;
        this.lastChat = lastChat;
        this.me = me;
    }

    @NonNull
    @Override
    public ChatItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_chat, parent, false);
        return new ChatItemHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatItemHolder holder, int position)
    {
        User friend = friends.get(position);
        String latest = lastChat.get(position);
        holder.uName.setText(friend.getUname());
        holder.latestChat.setText(latest);
//        holder.friendpPic.setBackground();
    }

    @Override
    public int getItemCount()
    {
        return friends.size();
    }

    public class ChatItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public final TextView uName, latestChat;
        public final ImageView friendpPic;
        final ChatAdapter cAdapter;

        public ChatItemHolder(@NonNull View itemView, ChatAdapter chatAdapter)
        {
            super(itemView);
            uName = itemView.findViewById(R.id.friendUName);
            latestChat = itemView.findViewById(R.id.lastChat);
            friendpPic = itemView.findViewById(R.id.itemChatImage);
            cAdapter = chatAdapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(view.getContext(), ChatActivity.class);
            String friendname = friends.get(getAdapterPosition()).getUname();
            i.putExtra("me", me);
            i.putExtra("friendname", friendname);
            context.startActivity(i);
        }
    }
}

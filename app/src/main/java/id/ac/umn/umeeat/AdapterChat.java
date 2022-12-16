package id.ac.umn.umeeat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdapterChat extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    GoogleMap mMap;

    List<String> listMessage;
    LayoutInflater layoutInflater;
    List<Bitmap> listphoto = new ArrayList<>();
    Bitmap photo;
    User me;
    String friendname;
    Context context;
    String longitude, latitude;
    private StorageReference mStorageRef;
    Bitmap bitmap;

    public AdapterChat(Context context, List<String> listMessage,User me, String friendname, Bitmap photo){
        this.context = context;
        this.listMessage = listMessage;
        this.me = me;
        this.friendname = friendname;
        this.photo = photo;
        listphoto.add(photo);
        listMessage = new ArrayList<>();
    }

    public AdapterChat(Context context, List<String> listMessage, User me, String friendname){
        this.context = context;
        this.listMessage = listMessage;
        this.me = me;
        this.friendname = friendname;
    }

    public int getItemViewType(int position){
        if(position < listMessage.size()) {
            String str = listMessage.get(position);
            String[] arrofstr = str.split(":", 3);
            if (arrofstr[1].equals("Text")) {
                if (arrofstr[0].equals(me.getUname())) {
                    return 0;
                } else if (arrofstr[0].equals(friendname)) {
                    return 1;
                }
            }else if (arrofstr[1].equals("Maps")) {
                if (arrofstr[0].equals(me.getUname())) {
                    return 2;
                } else if (arrofstr[0].equals(friendname)) {
                    return 3;
                }
            }else if(arrofstr[1].equals("Foto")){
                if (arrofstr[0].equals(me.getUname())) {
                    return 4;
                } else if (arrofstr[0].equals(friendname)) {
                    return 5;
                }
            }
        }
        return 3;
    }

    @Override
    public int getItemCount() {
        if (!listphoto.isEmpty()){
            return listMessage.size()+1;
        }
        return listMessage.size();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if(viewType == 0 ){
            view = layoutInflater.inflate(R.layout.item_container_sent, parent, false);
            return new id.ac.umn.umeeat.AdapterChat.HolderDataOne(view);
        }else if (viewType == 1){
            view = layoutInflater.inflate(R.layout.item_container_received, parent, false);
            return new id.ac.umn.umeeat.AdapterChat.HolderDataTwo(view);
        }else if (viewType == 2){
            view = layoutInflater.inflate(R.layout.item_container_sent, parent, false);
            return new AdapterChat.HolderDataMapSent(view);
        }else if (viewType == 3){
            view = layoutInflater.inflate(R.layout.item_container_sent, parent, false);
            return new AdapterChat.HolderDataMapReceived(view);
        }else if (viewType == 4){
            view = layoutInflater.inflate(R.layout.item_container_sent_photo, parent, false);
            return new id.ac.umn.umeeat.AdapterChat.HolderDataPhoto1(view);
        }
        else if (viewType == 5){
            view = layoutInflater.inflate(R.layout.item_container_received_photo, parent, false);
            return new id.ac.umn.umeeat.AdapterChat.HolderDataPhoto2(view);
        }
        view = layoutInflater.inflate(R.layout.item_container_sent_photo, parent, false);
        return new AdapterChat.HolderDataMapSent(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position < listMessage.size()) {
            String str = listMessage.get(position);
            String[] arrofstr = str.split(":", 3);
            if (arrofstr[1].equals("Text")) {
                if (arrofstr[0].equals(me.getUname())) {
                    id.ac.umn.umeeat.AdapterChat.HolderDataOne holderDataOne = (id.ac.umn.umeeat.AdapterChat.HolderDataOne) holder;
                    holderDataOne.tvsent.setText(arrofstr[2]);
                } else if (arrofstr[0].equals(friendname)) {
                    id.ac.umn.umeeat.AdapterChat.HolderDataTwo holderDataTwo = (id.ac.umn.umeeat.AdapterChat.HolderDataTwo) holder;
                    holderDataTwo.tvreceive.setText(arrofstr[2]);
                }
            } else if (arrofstr[1].equals("Maps")) {
                if (arrofstr[0].equals(me.getUname())) {
                    AdapterChat.HolderDataMapSent holderDataMapSent = (AdapterChat.HolderDataMapSent) holder;
                    String[] location = arrofstr[2].split(":", 4);
                    longitude = location[1];
                    latitude = location[3];
                    holderDataMapSent.tvsent.setText("https://www.google.com/maps/search/?api=1&query="+latitude+","+longitude);
                    holderDataMapSent.tvsent.setMovementMethod(LinkMovementMethod.getInstance());
                } else if (arrofstr[0].equals(friendname)) {
                    AdapterChat.HolderDataMapReceived holderDataMapReceived = (id.ac.umn.umeeat.AdapterChat.HolderDataMapReceived) holder;
                    String[] location = arrofstr[2].split(":", 4);
                    longitude = location[1];
                    latitude = location[3];
                    holderDataMapReceived.tvreceive.setText("https://www.google.com/maps/search/?api=1&query="+latitude+","+longitude);
                    holderDataMapReceived.tvreceive.setMovementMethod(LinkMovementMethod.getInstance());
                }
                // hhehehehehe
            } else if (arrofstr[1].equals("Foto")){
                if (arrofstr[0].equals(me.getUname())) {
                    try {
                        String filepath[] = arrofstr[2].split("/", 3);
                        String filename[] = filepath[2].split("\\.", 2);
                        Log.d("dion", filename[1]);
                        Log.d("dion", filepath[2]);
                        final File localFile = File.createTempFile(filename[0], "jpg");
                        mStorageRef = FirebaseStorage.getInstance().getReference().child(arrofstr[2]);
                        mStorageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                AdapterChat.HolderDataPhoto1 holderDataPhoto1 = (id.ac.umn.umeeat.AdapterChat.HolderDataPhoto1) holder;
                                holderDataPhoto1.ivphoto.setImageBitmap(bitmap);
                            }
                        });
                    } catch (IOException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else if (arrofstr[0].equals(friendname)){
                    try {
                        String filepath[] = arrofstr[2].split("/", 3);
                        String filename[] = filepath[2].split("\\.", 2);
                        final File localFile = File.createTempFile(filename[0], "jpg");
                        mStorageRef = FirebaseStorage.getInstance().getReference().child(arrofstr[2]);
                        mStorageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                AdapterChat.HolderDataPhoto2 holderDataPhoto2 = (id.ac.umn.umeeat.AdapterChat.HolderDataPhoto2) holder;
                                holderDataPhoto2.ivphoto.setImageBitmap(bitmap);
                            }
                        });
                    } catch (IOException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }


    class HolderDataOne extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        TextView tvsent;

        public HolderDataOne(@NonNull View itemView) {
            super(itemView);
            tvsent = itemView.findViewById(R.id.tvMessage);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("copy", tvsent.getText().toString());
            cm.setPrimaryClip(clip);
            Toast.makeText(context, "Text copied", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    class HolderDataTwo extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        TextView tvreceive;

        public HolderDataTwo(@NonNull View itemView) {
            super(itemView);
            tvreceive = itemView.findViewById(R.id.tvReceive);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("copy", tvreceive.getText().toString());
            cm.setPrimaryClip(clip);
            Toast.makeText(context, "Text copied", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    class HolderDataMapSent extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvsent;

        public HolderDataMapSent(@NonNull View itemView) {
            super(itemView);
            tvsent = itemView.findViewById(R.id.tvMessage);
            tvsent.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(tvsent.getText().toString()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    class HolderDataMapReceived extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvreceive;

        public HolderDataMapReceived(@NonNull View itemView) {
            super(itemView);
            tvreceive = itemView.findViewById(R.id.tvReceive);
            tvreceive.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(tvreceive.getText().toString()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
    class HolderDataPhoto1 extends RecyclerView.ViewHolder{

        ImageView ivphoto;

        public HolderDataPhoto1(@NonNull View itemView) {
            super(itemView);
            ivphoto = itemView.findViewById(R.id.ivPhoto);
        }
    }

    class HolderDataPhoto2 extends RecyclerView.ViewHolder{

        ImageView ivphoto;

        public HolderDataPhoto2(@NonNull View itemView) {
            super(itemView);
            ivphoto = itemView.findViewById(R.id.ivPhoto);
        }
    }
}
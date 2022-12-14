package id.ac.umn.umeeat;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterChat extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<String> listSent, listReceive, listMessage;
    LayoutInflater layoutInflater;
    List<Bitmap> listphoto = new ArrayList<>();
    Bitmap photo;
    User me;
    String friendname;

    public AdapterChat(Context context, List<String> listSent, List<String>listReceive, Bitmap photo){
        this.listSent = listSent;
        this.listReceive = listReceive;
        this.photo = photo;
        listphoto.add(photo);
        listMessage = new ArrayList<>();

        for(int i = 0; i<listReceive.size()+listSent.size(); i++){
            if(i < listSent.size())
                if(!listSent.get(i).isEmpty()){
                    listMessage.add(listSent.get(i));
                }
            if(i<listReceive.size())
                if(!listReceive.get(i).isEmpty()){
                    listMessage.add(listReceive.get(i));
                }
        }
    }

    public AdapterChat(Context context, List<String> listSent, List<String>listReceive, User me, String friendname){
        this.listSent = listSent;
        this.listReceive = listReceive;
        this.me = me;
        this.friendname = friendname;
        listMessage = new ArrayList<>();

        for(int i = 0; i<listReceive.size()+listSent.size(); i++){
            if(i < listSent.size())
                if(!listSent.get(i).isEmpty()){
                    listMessage.add(listSent.get(i));
                }
            if(i < listReceive.size())
                if(!listReceive.get(i).isEmpty()){
                    listMessage.add(listReceive.get(i));
                }
        }
    }

    public int getItemViewType(int position){
        if(position < listMessage.size()){
            String str = listMessage.get(position);
            String[] arrofstr = str.split(":",2);
            if(arrofstr[0].equals(me.getUname())){
                return 0;
            }else if(arrofstr[0].equals(friendname)){
                return 1;
            }else if(arrofstr[0].equals("meMaps")){
                return 2;
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
        layoutInflater = layoutInflater.from(parent.getContext());
        View view;
        if(viewType == 0 ){
            view = layoutInflater.inflate(R.layout.item_container_sent, parent, false);
            return new id.ac.umn.umeeat.AdapterChat.HolderDataOne(view);
        }else if (viewType == 1){
            view = layoutInflater.inflate(R.layout.item_container_received, parent, false);
            return new id.ac.umn.umeeat.AdapterChat.HolderDataTwo(view);
        }
        else if (viewType == 2){
            view = layoutInflater.inflate(R.layout.item_container_sent, parent, false);
            return new id.ac.umn.umeeat.AdapterChat.HolderDataTwo(view);
        }
        view = layoutInflater.inflate(R.layout.item_container_sent_photo, parent, false);
        return new id.ac.umn.umeeat.AdapterChat.HolderDataPhoto(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position < listMessage.size() ){
            String str = listMessage.get(position);
            String[] arrofstr = str.split(":",2);
            if(arrofstr[0].equals(me.getUname())){
                id.ac.umn.umeeat.AdapterChat.HolderDataOne holderDataOne =  (id.ac.umn.umeeat.AdapterChat.HolderDataOne) holder;
                holderDataOne.tvsent.setText(arrofstr[1]);
            } else if(arrofstr[0].equals(friendname)) {
                id.ac.umn.umeeat.AdapterChat.HolderDataTwo holderDataTwo =  (id.ac.umn.umeeat.AdapterChat.HolderDataTwo) holder;
                holderDataTwo.tvreceive.setText(arrofstr[1]);
            }
            else if(arrofstr[0].equals("meMpas")){
                id.ac.umn.umeeat.AdapterChat.HolderDataTwo holderDataTwo =  (id.ac.umn.umeeat.AdapterChat.HolderDataTwo) holder;
                holderDataTwo.tvreceive.setText(arrofstr[1]);
            }
        }else{
            id.ac.umn.umeeat.AdapterChat.HolderDataPhoto holderDataPhoto =  (id.ac.umn.umeeat.AdapterChat.HolderDataPhoto) holder;
            holderDataPhoto.ivphoto.setImageBitmap(photo);
        }


    }

    class HolderDataOne extends RecyclerView.ViewHolder{

        TextView tvsent;

        public HolderDataOne(@NonNull View itemView) {
            super(itemView);
            tvsent = itemView.findViewById(R.id.tvMessage);
        }
    }

    class HolderDataTwo extends RecyclerView.ViewHolder{

        TextView tvreceive;

        public HolderDataTwo(@NonNull View itemView) {
            super(itemView);
            tvreceive = itemView.findViewById(R.id.tvReceive);
        }
    }

    class HolderDataMapSent extends RecyclerView.ViewHolder{

        TextView tvreceive;

        public HolderDataMapSent(@NonNull View itemView) {
            super(itemView);
            tvreceive = itemView.findViewById(R.id.tvReceive);
        }
    }

    class HolderDataPhoto extends RecyclerView.ViewHolder{

        ImageView ivphoto;

        public HolderDataPhoto(@NonNull View itemView) {
            super(itemView);
            ivphoto = itemView.findViewById(R.id.ivPhoto);
        }
    }
}
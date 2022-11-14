package id.ac.umn.umeeat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterChat extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<String> listSent, listReceive, listMessage;
    LayoutInflater layoutInflater;

    public AdapterChat(Context context, List<String> listSent, List<String>listReceive){
        this.listSent = listSent;
        this.listReceive = listReceive;
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

    public int getItemViewType(int position){
        String str = listMessage.get(position);
        String[] arrofstr = str.split(":",2);
        if(arrofstr[0].equals("me")){
            return 0;
        }else{
            return 1;
        }


    }

    @Override
    public int getItemCount() {
        return listMessage.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = layoutInflater.from(parent.getContext());
        View view;
        if(viewType == 0 ){
            view = layoutInflater.inflate(R.layout.item_container_sent, parent, false);
            return new HolderDataOne(view);
        }
        view = layoutInflater.inflate(R.layout.item_container_received, parent, false);
        return new HolderDataTwo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String str = listMessage.get(position);
        String[] arrofstr = str.split(":",2);
        if(arrofstr[0].equals("me")){
            HolderDataOne holderDataOne =  (HolderDataOne) holder;
            holderDataOne.tvsent.setText(arrofstr[1]);
        } else if(arrofstr[0].equals("other")) {
            HolderDataTwo holderDataTwo =  (HolderDataTwo) holder;
            holderDataTwo.tvreceive.setText(arrofstr[1]);
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
}

package id.ac.umn.umeeat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private final List<User> listUser;
    Context context;
    User me;

    public SearchAdapter(Context context, List<User> listUser, User me)
    {
        this.context = context;
        this.listUser = listUser;
        this.me = me;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list, parent, false);
        return new SearchViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        final User data = listUser.get(position);
        holder.tvNama.setText(data.getUname());
        holder.tvJurusan.setText(data.getJurusan());
        holder.tvAngkatan.setText(data.getYear());

        String gender = data.getGender();
        if(gender.equals("Laki-Laki")){
            holder.ivGender.setImageResource(R.drawable.male);
        }
        else if (gender.equals("Perempuan")){
            holder.ivGender.setImageResource(R.drawable.female);
        }else{
            holder.ivGender.setImageResource(R.drawable.gender);
        }
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvNama, tvAngkatan, tvJurusan;
        ImageView ivGender;
        private SearchAdapter adapter;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            ivGender = itemView.findViewById(R.id.ivGender);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvAngkatan = itemView.findViewById(R.id.tvAngkatan);
            tvJurusan = itemView.findViewById(R.id.tvJurusan);
            itemView.setOnClickListener(this);
        }

        public SearchViewHolder linkAdapter(SearchAdapter searchAdapter) {
            this.adapter = searchAdapter;
            return this;
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(view.getContext(), ChatActivity.class);
            String friendname = listUser.get(getAdapterPosition()).getUname();
            Log.d("AccessChat", me.getUname()+"&"+friendname);
            i.putExtra("me", me);
            i.putExtra("friendname", friendname);
            context.startActivity(i);
        }
    }
}

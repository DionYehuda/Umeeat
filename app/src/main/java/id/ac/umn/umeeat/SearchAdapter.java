package id.ac.umn.umeeat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    List<User> listUser;
    public SearchAdapter(List<User> listUser){ this.listUser = listUser;}

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
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }
}

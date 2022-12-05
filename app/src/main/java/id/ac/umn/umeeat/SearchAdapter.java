package id.ac.umn.umeeat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    List<String> items;
    public SearchAdapter(List<String> items){ this.items = items;}

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list, parent, false);
        return new SearchViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.tvNama.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

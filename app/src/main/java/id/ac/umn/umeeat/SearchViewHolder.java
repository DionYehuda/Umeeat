package id.ac.umn.umeeat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchViewHolder extends RecyclerView.ViewHolder {

    TextView tvNama, tvAngkatan, tvJurusan;
    ImageView ivGender;
    private SearchAdapter adapter;

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);

        tvNama = itemView.findViewById(R.id.tvNama);
        tvAngkatan = itemView.findViewById(R.id.tvAngkatan);
        tvJurusan = itemView.findViewById(R.id.tvJurusan);
        tvNama.setOnClickListener(view -> {
            chatDetail();
        });
    }

    private void chatDetail() {
        Toast.makeText(itemView.getContext(), "FUnction buat ke chat details", Toast.LENGTH_SHORT).show();

    }

    public SearchViewHolder linkAdapter(SearchAdapter searchAdapter) {
        this.adapter = searchAdapter;
        return this;
    }
}

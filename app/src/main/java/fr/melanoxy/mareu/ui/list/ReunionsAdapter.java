package fr.melanoxy.mareu.ui.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import fr.melanoxy.mareu.databinding.ListAdapterBinding;

public class ReunionsAdapter extends ListAdapter<ReunionsViewStateItem, ReunionsAdapter.MyViewHolder> {

    private final OnReunionClickedListener listener;


    public ReunionsAdapter(OnReunionClickedListener listener) {
        super(new ListReunionItemCallback());

        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(ListAdapterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(getItem(position), listener);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ListAdapterBinding binding;

        public MyViewHolder(@NonNull ListAdapterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ReunionsViewStateItem item, OnReunionClickedListener listener) {
            itemView.setOnClickListener(v -> listener.onReunionClicked(item.getId()));
            binding.reunionsItemTvFieldTop.setText(item.getFieldTop());
            binding.neighboursItemTvFieldBottom.setText(item.getFieldBottom());
            binding.reunionsItemIvDelete.setOnClickListener(v -> listener.onDeleteReunionClicked(item.getId()));
        }

    }


    private static class ListReunionItemCallback extends DiffUtil.ItemCallback<ReunionsViewStateItem> {
        @Override
        public boolean areItemsTheSame(@NonNull ReunionsViewStateItem oldItem, @NonNull ReunionsViewStateItem newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ReunionsViewStateItem oldItem, @NonNull ReunionsViewStateItem newItem) {
            return oldItem.equals(newItem);
        }
    }

}

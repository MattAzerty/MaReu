package fr.melanoxy.mareu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder>{

String[] list;

    public ListAdapter(String[] list) {
        this.list = list;}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mTextView.setText(list[position]);

    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mTextView;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            mTextView = itemView.findViewById(R.id.textView);

        }
    }

}

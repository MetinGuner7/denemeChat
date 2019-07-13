package com.example.denemechat;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>  {

    private List<Chat> chatMessages;

    public RecyclerViewAdapter(List<Chat> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list_row,parent,false);

        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String chatMessage = chatMessages.get(position).getMessage();
        String chatUser = chatMessages.get(position).getUserName();
        String userImageURL = chatMessages.get(position).getImgURL();

        holder.chatMessage.setText(chatMessage);
        holder.userName.setText(chatUser);
        if (position %2 == 0){

            holder.lnTema.setBackgroundColor(Color.parseColor("#FFDFC4"));

            //((LinearLayout.LayoutParams) holder.lnTema.getLayoutParams()).setMargins(50, 0, 0, 0);
        }

        //Picasso.get().load(userImageURL).into(holder.imgProfil);
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout lnTema;
        public TextView chatMessage;
        public TextView userName;
        public ImageView imgProfil;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            lnTema = itemView.findViewById(R.id.lnRecyclerRowTema);
            chatMessage = itemView.findViewById(R.id.recycler_text_view);
            userName = itemView.findViewById(R.id.recycler_text_profil_name);
            //imgProfil = itemView.findViewById(R.id.imgProfil_default);

        }
    }

}

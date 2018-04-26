package com.worksmobile.android.botproject.feature.chat.newchat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.model.Invitable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2018. 4. 25..
 */

public class InvitableAdapter extends RecyclerView.Adapter<InvitableAdapter.InvitableHolder> {
    private LayoutInflater inflater;
    private List<Invitable> invitables = new ArrayList<>();
    InvitableClickListener listener;

    public InvitableAdapter(Context context, List<Invitable> invitables, InvitableClickListener listener) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.invitables = invitables;
        this.listener = listener;
    }

    @Override
    public InvitableHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_newchat_invitable, parent, false);
        return new InvitableHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(InvitableHolder holder, int position) {
        Invitable invitable = invitables.get(position);
        holder.bindInvitable(invitable);
    }

    @Override
    public int getItemCount() {
        return invitables.size();
    }

    static class InvitableHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_newchat_image)
        ImageView imageView;
        @BindView(R.id.item_newchat_nickname)
        TextView nicknameTextView;
        @BindView(R.id.layout_newchat_item)
        ViewGroup layout;

        public InvitableHolder(View itemView, final InvitableClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onHolderClick(getAdapterPosition());
                }
            });
        }

        public void bindInvitable(Invitable invitable) {
            //TODO check invitable which type is
            if (invitable.getType() == 1) {
                this.imageView.setImageResource(R.drawable.ic_person_black_48dp);
            }
            if (invitable.getType() == 2) {
                this.imageView.setImageResource(R.drawable.ic_android_black_48dp);
            }

            this.nicknameTextView.setText(invitable.getName());
        }
    }
}

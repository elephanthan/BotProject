package com.worksmobile.android.botproject.feature.chat.newchat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.model.Talker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2018. 4. 25..
 */

public class InvitableAdapter extends RecyclerView.Adapter<InvitableAdapter.InvitableHolder> {
    private LayoutInflater inflater;
    private List<Talker> talkers = new ArrayList<>();
    InvitableClickListener listener;

    public InvitableAdapter(Context context, List<Talker> talkers, InvitableClickListener listener) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.talkers = talkers;
        this.listener = listener;
    }

    @Override
    public InvitableHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_newchat_invitable, parent, false);
        return new InvitableHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(InvitableHolder holder, int position) {
        Talker talker = talkers.get(position);
        holder.bindInvitable(talker);
    }

    @Override
    public int getItemCount() {
        return talkers.size();
    }

    static class InvitableHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.item_newchat_checkbox)
        CheckBox checkBox;
        @BindView(R.id.item_newchat_image)
        ImageView imageView;
        @BindView(R.id.item_newchat_nickname)
        TextView nicknameTextView;
        @BindView(R.id.layout_newchat_item)
        ViewGroup layout;

        public InvitableHolder(View itemView, final InvitableClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCheckBoxClick(getAdapterPosition());
                }
            });
        }

        public void bindInvitable(Talker talker) {
            //TODO check talker which type is
            if (talker.getType() == 1) {
                this.imageView.setImageResource(R.drawable.ic_person_black_48dp);
            }
            if (talker.getType() == 2) {
                this.imageView.setImageResource(R.drawable.ic_android_black_48dp);
            }

            this.nicknameTextView.setText(talker.getName());
            this.checkBox.setChecked(talker.isChecked());
        }
    }
}

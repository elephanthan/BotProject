package com.worksmobile.android.botproject.feature.chat.newchat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.worksmobile.android.botproject.R;
import com.worksmobile.android.botproject.model.Talker;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2018. 4. 25..
 */

public class TalkerAdapter extends RecyclerView.Adapter<TalkerAdapter.TalkerHolder> {
    private LayoutInflater inflater;
    private List<? extends Talker> talkers;
    private TalkerClickListener listener;
    private static Context context;

    public TalkerAdapter(Context context, @NonNull List<? extends Talker> talkers, TalkerClickListener listener) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.talkers = talkers;
        this.listener = listener;
    }

    @Override
    public TalkerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_newchat_talker, parent, false);
        return new TalkerHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(TalkerHolder holder, int position) {
        Talker talker = talkers.get(position);
        holder.bindInvitable(talker);
    }

    @Override
    public int getItemCount() {
        if (talkers == null) {
            return 0;
        }
        return talkers.size();
    }

    public void updateCheckBox(int position) {
        Talker talker = getItem(position);
        talker.setChecked(!talker.isChecked());
        notifyDataSetChanged();
    }

    private Talker getItem(int position) {
        return talkers.get(position);
    }

    static class TalkerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_newchat_checkbox)
        CheckBox checkBox;
        @BindView(R.id.item_newchat_image)
        ImageView profileImageView;
        @BindView(R.id.item_newchat_nickname)
        TextView nicknameTextView;
        @BindView(R.id.layout_newchat_item)
        ViewGroup layout;

        public TalkerHolder(View itemView, final TalkerClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.checkBox.setOnClickListener(v -> listener.onCheckBoxClick(getAdapterPosition()));
            this.layout.setOnClickListener(v -> listener.onHolderClick(getAdapterPosition()));
        }

        public void bindInvitable(Talker talker) {
            int defaultImage = R.drawable.ic_icon_man;
            if (talker.getType() == Talker.TALKER_TYPE_BOT) {
                defaultImage = R.drawable.ic_icon_bot;
            }
            Glide.with(context).load(talker.getProfile()).placeholder(defaultImage).error(defaultImage).into(profileImageView);

            this.nicknameTextView.setText(talker.getName());
            this.checkBox.setChecked(talker.isChecked());
        }
    }
}

package com.kanna.ww;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kanna.ww.R;

public class MessageAdapter extends ArrayAdapter<Message> {
    private int resourceId;
    MessageAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Message> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Message message = getItem(position);
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView playerNameTextView = view.findViewById(R.id.playerName);
        TextView messageTextView = view.findViewById(R.id.every_message);
        assert message != null;
        playerNameTextView.setText(message.getPlayerName() + ": ");
        messageTextView.setText(message.getMessage());
        return view;
    }
}

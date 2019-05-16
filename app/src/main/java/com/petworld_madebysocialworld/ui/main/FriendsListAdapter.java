package com.petworld_madebysocialworld.ui.main;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import Models.Friend;
import com.petworld_madebysocialworld.FriendsSingleton;
import com.petworld_madebysocialworld.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FriendsListAdapter extends ArrayAdapter<Friend> implements ListAdapter {
    private FriendsSingleton friendsSingleton;
    private ArrayList<Friend> friendsListInfo;
    private Context context;

    public FriendsListAdapter(Context context, int textViewResourceid) {
        super(context, textViewResourceid);
        friendsSingleton = FriendsSingleton.getInstance();
        friendsListInfo = friendsSingleton.getFriendsListInfo();
        this.context = context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public int getCount() {
        return friendsListInfo.size();
    }

    @Override
    public Friend getItem(int position) {
        return friendsListInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Friend friendData = friendsListInfo.get(position);
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.friends_list_row, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            final TextView name = convertView.findViewById(R.id.name);
            ImageView image = convertView.findViewById(R.id.imageView);
            Button button = convertView.findViewById(R.id.button);
            name.setText(friendData.getName());
            Picasso.get().load(friendData.getImageURL()).into(image);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (removeFriend(friendData.getId())) notifyDataSetChanged();
                    Snackbar.make(v, friendData.getName() + " " + position, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            if (friendData.getId().equals("NoFriends")) button.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    public boolean addFriend(Friend friend) {
        // ADD EN FIREBASE
        boolean added = friendsListInfo.add(friend);
        notifyDataSetChanged();
        return added;
    }

    public boolean removeFriend(String newfriendId) {
        for (int i = 0; i < friendsListInfo.size(); ++i) {
            // TEST - NO SE SI friendsListInfo.remove(i) != null se cumple
            if (friendsListInfo.get(i).getId().equals(newfriendId) && friendsListInfo.remove(i) != null) {
                // DELETE FROM FIREBASE
                notifyDataSetChanged();
                return true;
            }
        }

        return false;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return friendsListInfo.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

}

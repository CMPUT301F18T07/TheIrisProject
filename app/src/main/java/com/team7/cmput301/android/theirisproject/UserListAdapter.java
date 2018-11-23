package com.team7.cmput301.android.theirisproject;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.team7.cmput301.android.theirisproject.activity.ViewProfileActivity;
import com.team7.cmput301.android.theirisproject.model.User;

import java.util.List;

/**
 * UserListAdapter holds a List of Users, which is used in the ListView for
 * ViewProfileActivity
 *
 * @author Jmmxp
 * @see ViewProfileActivity
 */
public class UserListAdapter extends ArrayAdapter<User> {

    private Activity context;
    private int resource;
    private List<User> users;

    public UserListAdapter(@NonNull Activity context, int resource, @NonNull List<User> users) {
        super(context, resource, users);
        this.context = context;
        this.resource = resource;
        this.users = users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(resource, parent, false);

        TextView name = view.findViewById(R.id.care_provider_name_text_view);
        TextView phone = view.findViewById(R.id.care_provider_phone_text_view);

        User user = users.get(position);

        name.setText(user.getName());
        phone.setText(user.getPhone());

        return view;
    }
}

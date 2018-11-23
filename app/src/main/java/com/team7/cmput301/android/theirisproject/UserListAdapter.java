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

import org.w3c.dom.Text;

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
    private List<? extends User> users;

    public UserListAdapter(@NonNull Activity context, int resource, @NonNull List<? extends User> users) {
        super(context, resource, (List<User>) users);
        this.context = context;
        this.resource = resource;
        this.users = users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(resource, parent, false);

        TextView name = view.findViewById(R.id.user_name_text_view);
        TextView email = view.findViewById(R.id.user_email_text_view);
        TextView phone = view.findViewById(R.id.user_phone_text_view);

        User user = users.get(position);

        name.setText(user.getName());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());

        return view;
    }
}

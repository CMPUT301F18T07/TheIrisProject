package com.team7.cmput301.android.theirisproject;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.team7.cmput301.android.theirisproject.model.CareProvider;
import com.team7.cmput301.android.theirisproject.model.Patient;

import java.util.List;

public class CareProviderListAdapter extends ArrayAdapter<CareProvider> {

    private Activity context;
    private int resource;
    private List<CareProvider> careProviders;

    public CareProviderListAdapter(@NonNull Activity context, int resource, @NonNull List<CareProvider> careProviders) {
        super(context, resource, careProviders);
        this.context = context;
        this.resource = resource;
        this.careProviders = careProviders;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(resource, parent, false);

        TextView name = view.findViewById(R.id.care_provider_name_text_view);
        TextView phone = view.findViewById(R.id.care_provider_phone_text_view);

        CareProvider careProvider = careProviders.get(position);

        name.setText(careProvider.getName());
        phone.setText(careProvider.getPhone());

        return view;
    }
}

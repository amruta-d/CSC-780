package com.termproject.familyprotector;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Mehul on 12/3/2015.
 */
public class ChildAlertRecylerAdapter extends RecyclerView.Adapter<ChildAlertRecylerAdapter.ChildAlertViewHolder> {

    private List<ParseObject> mChildAlerts;
    private String mChildName;

    public static class ChildAlertViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ChildAlertViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("alert adapter", "Element " + getPosition() + " clicked.");
                }
            });
            textView = (TextView) v.findViewById(R.id.child_alert_textView);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public ChildAlertRecylerAdapter(List<ParseObject> childAlerts, String childName) {

        mChildAlerts = childAlerts;
        mChildName = childName;
    }


    @Override
    public ChildAlertViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.child_alert_row_item, viewGroup, false);

        return new ChildAlertViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ChildAlertViewHolder holder, int position) {

        if(mChildAlerts.size()>0) {

            ParseObject alert = mChildAlerts.get(position);
            Date date = alert.getCreatedAt();
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy ");
            SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
            String dateStr = formatter.format(date);
            String timeStr = formatTime.format(date);


            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            holder.getTextView().setText(mChildName + " " + mChildAlerts.get(position).getString("alert") +
                    " on " + dateStr+ " at "+ timeStr+ " hrs.");
        }
        else {
            holder.getTextView().setText("No Alerts for "+ mChildName);
        }

    }

    @Override
    public int getItemCount() {
        return mChildAlerts.size();
    }

}

package com.termproject.familyprotector;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.Date;
import java.util.List;

/**
 * Created by Mehul on 12/3/2015.
 */
public class ChildAlertRecylerAdapter extends RecyclerView.Adapter<ChildAlertRecylerAdapter.ChildAlertViewHolder> {

//    private String[] mDataSet;
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
//        mDataSet = dataSet;
        Log.v("size", "the size is:"+ childAlerts.size());
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

        Log.d("child alert adapter", "Element " + position + " set.");
        if(mChildAlerts.size()>0) {

            ParseObject alert = mChildAlerts.get(position);
            Date date = alert.getCreatedAt();
            Log.v("time", "t:" + date.getTime());


            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            holder.getTextView().setText(mChildName + " " + mChildAlerts.get(position).getString("alert") +
                    " at " + date);
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

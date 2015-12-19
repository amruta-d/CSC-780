package com.termproject.familyprotector;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Mehul on 12/17/2015.
 */
public class ChildRuleRecyclerAdapter extends RecyclerView.Adapter<ChildRuleRecyclerAdapter.ChildRuleViewHolder> {
    private List<ParseObject> mChildRules;
    private String mChildName;

    public static class ChildRuleViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ChildRuleViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("rule adapter", "Element " + getPosition() + " clicked.");
                }
            });
            textView = (TextView) v.findViewById(R.id.child_rule_textView);
        }

        public TextView getTextView() {
            return textView;
        }
    }
    public ChildRuleRecyclerAdapter(List<ParseObject> childAlerts, String childName) {
        mChildRules = childAlerts;
        mChildName = childName;
    }

    @Override
    public ChildRuleViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.child_rule_row_item, viewGroup, false);

        return new ChildRuleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ChildRuleViewHolder holder, int position) {

        if(mChildRules.size()>0) {

            ParseObject rule = mChildRules.get(position);
            holder.getTextView().setText("Rule for:    " + rule.getString("locationName") + "\n" +
                    " From: " + rule.getString("ruleFromTime")+ "    " +
                     "To: " + rule.getString("ruleToTime"));
        }
        else {
            holder.getTextView().setText("No Rules for "+ mChildName);
        }

    }

    @Override
    public int getItemCount() {
        return mChildRules.size();
    }
}

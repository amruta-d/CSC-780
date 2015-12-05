package com.termproject.familyprotector;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ChildRuleFragment extends Fragment {
    FloatingActionButton addRuleFloatingActionButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_child_rule, container, false);
        addRuleFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.add_rule_floating_action_button);
        addRuleFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                addRuleActivity();
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }

}

package com.lymno.cabinet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.ViewHolder> {

    private ArrayList<Day> timetableData; // these are the things we want to display

    public TimetableAdapter(ArrayList<Day> days) {
        this.timetableData = days;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TimetableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timetable_adapter, parent, false);

        // create ViewHolder
        return new ViewHolder(itemLayoutView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData

        Day questsDataPos = timetableData.get(position);
        viewHolder.dayName.setText(questsDataPos.getName());
        viewHolder.todayTable.setText(questsDataPos.getTodayTable());
    }

    public void updateItems (ArrayList<Day> items) {
        this.timetableData = items;
        notifyDataSetChanged();
    }

    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView dayName;
        public TextView todayTable;
        //public ImageView imgViewIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            dayName = (TextView) itemLayoutView.findViewById(R.id.dayName);
            todayTable = (TextView) itemLayoutView.findViewById(R.id.todayTable);

            //imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.item_icon);
            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            /*
            Intent questInfoIntent = new Intent(context, QuestInfo.class);
            questInfoIntent.putExtra("questId", timetableData.get(getAdapterPosition()).getId());
            questInfoIntent.putExtra("amountStages", timetableData.get(getAdapterPosition()).getAmountStages());
            questInfoIntent.putExtra("questDescription", timetableData.get(getAdapterPosition()).getDescription());
            questInfoIntent.putExtra("questLength", timetableData.get(getAdapterPosition()).getLength());
            questInfoIntent.putExtra("questName", timetableData.get(getAdapterPosition()).getName());
            context.startActivity(questInfoIntent);
            */
        }
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return timetableData.size();
    }
}


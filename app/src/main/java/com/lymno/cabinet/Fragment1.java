package com.lymno.cabinet;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment {
    public TimetableAdapter mAdapter;
    private SwipeRefreshLayout refreshLayout;

    public ArrayList<Day> list = new ArrayList<Day>();
//    private QuestsDataBase db = new QuestsDataBase(this);
    Context context;

    final String LOG_TAG = "myLogs";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment1, null);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new updateTimetable().execute("api/school/timetable");
//        list.add(new Subject(1, "Math", "Monday"));
//        list.add(new Subject(2, "Math", "Monday"));

        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.quests_list_recycler_list);
        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        // 3. create an adapter
        mAdapter = new TimetableAdapter(list);
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private class updateTimetable extends BasicRequest {
        protected void onPostExecute(String JSONString) {
            try {
                JSONArray dayArray = new JSONArray(JSONString);
//                db.recreateDataBase(new ArrayList<>());
                JSONArray subjectArray;
                JSONObject dayJSON;
                JSONObject subjectJSON;
                try {
                    for (int i = 0; i < dayArray.length(); ++i) {
                        dayJSON = dayArray.getJSONObject(i);
                        final String name = dayJSON.getString("Name");
                        subjectArray = new JSONArray(dayJSON.getString("Subjects"));
                        ArrayList<Subject> subjects = new ArrayList<Subject>();
                        for (int j = 0; j < subjectArray.length(); ++j) {
                            subjectJSON = subjectArray.getJSONObject(j);
                            subjects.add(new Subject(subjectJSON.getInt("Number"), subjectJSON.getString("Name")));
                        }
                        Day day = new Day(name, subjects);
                        list.add(day);

                    }
                }
                catch (JSONException ex) {
                    ex.printStackTrace();
                }

            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            mAdapter.updateItems(list);
        }
    }


}
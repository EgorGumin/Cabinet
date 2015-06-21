package com.lymno.cabinet;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentMarks extends Fragment {
    public MarksAdapter mAdapter;
    String strtext;

    public ArrayList<DayMark> list = new ArrayList<DayMark>();
    Context context;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        strtext = getArguments().getString("edttext");
        if (strtext.equals("")){
            strtext = "api/school/timetable?School=34&Class=9";
        }
        return inflater.inflate(R.layout.fragment1, null);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new updateTimetable().execute(strtext);

        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.quests_list_recycler_list);
        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        // 3. create an adapter
        mAdapter = new MarksAdapter(list);
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
                        final String date = dayJSON.getString("Date");
                        subjectArray = new JSONArray(dayJSON.getString("OneSubjects"));
                        ArrayList<SubjectMarks> subjects = new ArrayList<SubjectMarks>();
                        for (int j = 0; j < subjectArray.length(); ++j) {
                            subjectJSON = subjectArray.getJSONObject(j);
                            subjects.add(new SubjectMarks(subjectJSON.getInt("Number"), subjectJSON.getString("Name"), subjectJSON.getInt("Mark")));
                        }
                        DayMark day = new DayMark(name, date, subjects);
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
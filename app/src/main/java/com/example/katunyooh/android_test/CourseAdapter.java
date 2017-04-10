package com.example.katunyooh.android_test;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by BOY on 10/4/2560.
 */

public class CourseAdapter extends BaseAdapter{

    private Context context;
    private String[]subjectStrings, descripStrings;

    public CourseAdapter(Context context,
                         String[] subjectStrings,
                         String[] descripStrings) {
        this.context = context;
        this.subjectStrings = subjectStrings;
        this.descripStrings = descripStrings;
    }

    @Override
    public int getCount() {
        return subjectStrings.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.course_listview,parent,false);

        //For Subject

        TextView subjectTextView = (TextView) view1.findViewById(R.id.txtSubject);
        subjectTextView.setText(subjectStrings[position]);

        //For Descrip
        TextView descripTextView = (TextView)view1.findViewById(R.id.txtDescrip);
        descripTextView.setText(descripStrings[position]);


        return view1;
    }
}   //main Class

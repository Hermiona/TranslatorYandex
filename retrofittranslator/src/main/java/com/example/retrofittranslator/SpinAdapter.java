package com.example.retrofittranslator;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.retrofittranslator.models.LangItem;

import java.util.ArrayList;

/**
 * Created by ASUS on 18.01.2018.
 */

public class SpinAdapter extends ArrayAdapter<LangItem> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ArrayList<LangItem> values;

    public SpinAdapter(Context context, int textViewResourceId,
                       ArrayList<LangItem> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount(){
        return values.size();
    }

    @Override
    public LangItem getItem(int position){
        return values.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setTextSize(18);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(values.get(position).getDescription());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item=inflater.inflate(R.layout.spinner_item, parent, false);
        TextView label=(TextView) item.findViewById(R.id.txtSpinItem);

//        TextView label = new TextView(context);
//        label.setTextColor(Color.BLACK);
        label.setTextSize(18);
        label.setText(values.get(position).getDescription());

        return label;
    }
}

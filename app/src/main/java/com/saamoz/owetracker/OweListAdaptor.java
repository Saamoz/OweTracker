package com.saamoz.owetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class OweListAdaptor extends BaseAdapter {
    String[] descriptionArray, valueArray;
    Context myContext;

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(myContext);
        View oweRow = inflater.inflate(R.layout.oweitem, parent, false);

        TextView description = (TextView) oweRow.findViewById(R.id.descText);
        TextView value = (TextView) oweRow.findViewById(R.id.valText);
        Button removeOwe = (Button) oweRow.findViewById(R.id.removeOweButton);

        description.setText(descriptionArray[position]);
        value.setText(valueArray[position]);

        removeOwe.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(myContext instanceof MainActivity){
                            ((MainActivity)myContext).removeOwe(position);
                        }
                    }
                }
        );

        return oweRow;
    }

    public OweListAdaptor(String[] description, String[] value, Context context) {
        this.descriptionArray = description;
        this.valueArray = value;
        this.myContext = context;
    }

    public void updateValues(String[] newList){
        for (int i=0; i< newList.length; i++){
            valueArray[i] = newList[i];
        }
        this.notifyDataSetChanged();
    }

    public void updateDescriptions(String[] newList){
        for (int i=0; i< newList.length; i++){
            descriptionArray[i] = newList[i];
        }
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}

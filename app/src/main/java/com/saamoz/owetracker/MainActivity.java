package com.saamoz.owetracker;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String TAG = "com.saamoz.owetracker";
    OweDBManager DB;
    OweListAdaptor oweAdapter;
    String[] descriptionList, valueList;
    Context context;
    ListView oweList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "Started");

        DB = new OweDBManager(this, null, null, 1);

        test();

        /*
        oweAdapter = new OweListAdaptor(descriptionList, valueList, context);
        oweList = (ListView) findViewById(R.id.oweList);
        oweList.setAdapter(oweAdapter);

        updateLists();
        */
    }

    private void test() {

    }

    String sumArray(String[] array){
        int sum = 0;
        for (int i=0; i< array.length; i++){
            sum += i;
        }
        return String.valueOf(sum);
    }
    public void addAnOwe(View v){
        EditText description = (EditText) findViewById(R.id.descriptionAdd);
        EditText value = (EditText) findViewById(R.id.valueAdd);
        Owe newOwe = new Owe(description.getText().toString(), Integer.parseInt(value.getText().toString()));
        DB.addOwe(newOwe);
        Log.i(TAG, "Attempting to add Owe");

        updateLists();
    }

    public void updateLists(){
        descriptionList = DB.DBtoArray("desc");
        valueList = DB.DBtoArray("value");
        oweAdapter.updateValues(valueList);
        oweAdapter.updateDescriptions(descriptionList);
        updateTotalViewText();
    }

    public void updateTotalViewText(){
        TextView totalOwes = (TextView) findViewById(R.id.totalOwes);
        totalOwes.setText("Moiz owes a total of: " + sumArray(valueList));
    }

    public void removeOwe(int position){
        String item = String.valueOf(oweList.getItemAtPosition(position));
        DB.removeOwes(item, 1);
    }
}

package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;
    DatabaseHelper databaseHelper = new DatabaseHelper(HistoryActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        RecyclerView recyclerView = findViewById(R.id.recycler_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, databaseHelper.getAll());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }


    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        ModelBMI clickedBMI = adapter.getItem(position);
        databaseHelper.deleteOne(clickedBMI);

        adapter.updateData(databaseHelper.getAll());
        runOnUiThread(() -> adapter.notifyDataSetChanged());
    }

    @Override
    public void onResume(){
        super.onResume();
        adapter.updateData(databaseHelper.getAll());
        adapter.notifyDataSetChanged();
    }

}
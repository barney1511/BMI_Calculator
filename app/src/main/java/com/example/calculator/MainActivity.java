package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{

    TextView BMI;
    EditText weight, height;
    Button calculate, viewHistory;
    MyRecyclerViewAdapter adapter;

    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BMI = findViewById(R.id.bmi_number);
        weight = findViewById(R.id.editTextNumber);
        height = findViewById(R.id.editTextNumber2);
        calculate = findViewById(R.id.button2);
        viewHistory = findViewById(R.id.button3);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, databaseHelper.getAll());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        calculate.setOnClickListener((v) -> {

            ModelBMI bmiModel;

            try {
                float height_value = (float) Integer.parseInt(height.getText().toString()) / 100;
                int bmi_result = Math.round(Integer.parseInt(weight.getText().toString()) / (height_value*height_value));
                BMI.setText(String.valueOf(bmi_result));

                bmiModel = new ModelBMI(bmi_result,Integer.parseInt(weight.getText().toString()),Integer.parseInt(height.getText().toString()));
               //Toast.makeText(MainActivity.this, bmiModel.toString(), Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(MainActivity.this, "Calculation error. Try again.", Toast.LENGTH_SHORT).show();
                bmiModel = new ModelBMI(-1, -1,-1);
            }

            boolean success = databaseHelper.addOne(bmiModel);

            adapter.updateData(databaseHelper.getAll());
            runOnUiThread(() -> adapter.notifyDataSetChanged());

            //Toast.makeText(MainActivity.this, "Success = " + success, Toast.LENGTH_SHORT).show();
        });

        viewHistory.setOnClickListener((v) -> {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        adapter.updateData(databaseHelper.getAll());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
package com.example.question;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.question.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String name = getIntent().getStringExtra("name");
        if (name != null) {
            binding.etName.setText(name);
        }
        binding.tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.etName.getText().toString();
                if (name.isEmpty()) {
                    Snackbar.make(binding.getRoot(), "enter your name", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
                finish();
            }
        });
    }

}
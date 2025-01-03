package com.example.todo1;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private LinearLayout container;  // The LinearLayout container for to-do items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);  // Initialize the LinearLayout
        if (container == null) {
            Log.e("MainActivity", "Container not found!");
            return;
        }

        @SuppressLint("WrongViewCast") FloatingActionButton fabAdd = findViewById(R.id.fabAdd);  // Floating Action Button

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTodoDialog();  // Show dialog to add new to-do item
            }
        });
    }

    // Display a dialog to add a new to-do item
    private void showAddTodoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("TODO");

        final EditText input = new EditText(this);
        input.setHint("Enter your todo");
        builder.setView(input);

        builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String todoText = input.getText().toString().trim();
                if (!todoText.isEmpty()) {
                    addNewTodo(todoText);  // Add the new to-do item to the container
                } else {
                    Toast.makeText(MainActivity.this, "Todo cannot be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("CANCEL", null);

        builder.show();
    }

    // Add the new to-do item to the container
    private void addNewTodo(String todoText) {
        final TextView newTodo = new TextView(this);
        newTodo.setText(todoText);
        newTodo.setTextSize(16);
        newTodo.setPadding(16, 16, 16, 16);
        newTodo.setGravity(Gravity.START);
        newTodo.setBackgroundResource(android.R.color.darker_gray);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 8, 0, 8);
        newTodo.setLayoutParams(params);

        newTodo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("MainActivity", "Long click detected on todo: " + newTodo.getText());
                showDeleteConfirmationDialog(newTodo);  // Show delete confirmation dialog
                return true;
            }
        });

        container.addView(newTodo);  // Add the new to-do item to the container
    }

    // Show a confirmation dialog for deleting a to-do item
    private void showDeleteConfirmationDialog(final TextView todo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setMessage("Confirm delete?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                container.removeView(todo);  // Remove the to-do item from the container
                Toast.makeText(MainActivity.this, "Todo deleted", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("NO", null);

        builder.show();
    }
}

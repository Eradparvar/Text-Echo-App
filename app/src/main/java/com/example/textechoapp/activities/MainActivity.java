package com.example.textechoapp.activities;

import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.textechoapp.R;
import com.example.textechoapp.lib.Utils;
import com.example.textechoapp.model.UserEntryList;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private UserEntryList mUserEntryList;
    private boolean mShowHistory;
    private TextView textViewEntriesList;
    private Editable userInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        TextView textView = findViewById(R.id.textView);
        TextInputEditText textInputEditText = findViewById(R.id.textInputEditText);


        textViewEntriesList = findViewById(R.id.entriesList);
        mUserEntryList = new UserEntryList();
        textViewEntriesList.setText(mUserEntryList.getUserEntriesListAsString());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInput = textInputEditText.getText();
                textView.setText(userInput);
                mUserEntryList.addEntryToList(userInput.toString());
                displayEntries();
            }
        });
    }

    private void displayEntries() {
        if (mShowHistory) {
            textViewEntriesList.setText(mUserEntryList.getUserEntriesListAsString());
        } else if (!mShowHistory) {
            textViewEntriesList.setText("");

        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_display_prior_entries).setChecked(mShowHistory);
        displayEntries();
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_display_prior_entries) {
            toggleMenuItem(item);
            mShowHistory = item.isChecked();
            displayEntries();
            return true;

        } else if (id == R.id.action_about) {
            Utils.showInfoDialog(MainActivity.this,
                    R.string.about, R.string.about_text);
            return true;

        } else if (id == R.id.action_clear_prior_entries) {
            mUserEntryList.clearUserEntries();
            textViewEntriesList.setText("");
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleMenuItem(MenuItem item) {
        item.setChecked(!item.isChecked());
    }


    //ToDo
//    override onSaveInstanceState and onRestoreInstanceState to preserve the EntryList field object using GSON and
//    to preserve the show/hide entries history state by saving/restoring the boolean field above.
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
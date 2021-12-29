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
    private boolean mShowHistory = true;
    private TextView textViewEntriesList;
    private Editable userInput;
    private TextView headerEntriesList;
    private TextView textView;
    private TextInputEditText textInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupFields();
        setupFAB();
    }

    private void setupFAB() {
        ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInput = textInputEditText.getText();
                mUserEntryList.addEntryToList(userInput.toString());
                textViewEntriesList.setText(mUserEntryList.getUserEntriesListAsString());
                textView.setText(mUserEntryList.getLastUserEntry());

            }
        });
    }

    private void setupFields() {
        textView = findViewById(R.id.textViewEchoText);
        textInputEditText = findViewById(R.id.textInputEditText);
        headerEntriesList = findViewById(R.id.headerEntriesList);
        textViewEntriesList = findViewById(R.id.entriesList);
        mUserEntryList = new UserEntryList();
        textViewEntriesList.setText(mUserEntryList.getUserEntriesListAsString());
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void toggleDisplayEntries() {
        if (mShowHistory) {
            textViewEntriesList.setVisibility(View.VISIBLE);
            headerEntriesList.setVisibility(View.VISIBLE);
        } else if (!mShowHistory) {
            textViewEntriesList.setVisibility(View.INVISIBLE);
            headerEntriesList.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_display_prior_entries).setChecked(mShowHistory);
        toggleDisplayEntries();
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
            toggleDisplayEntries();
            return true;

        } else if (id == R.id.action_about) {
            Utils.showInfoDialog(MainActivity.this,
                    R.string.about, R.string.about_text);
            return true;

        } else if (id == R.id.action_clear_prior_entries) {
            mUserEntryList.clearUserEntries();
            textViewEntriesList.setText(mUserEntryList.getUserEntriesListAsString());
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleMenuItem(MenuItem item) {
        item.setChecked(!item.isChecked());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("mShowHistory", mShowHistory);
        outState.putString("userEntryList", mUserEntryList.getJSONStringFromThis());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        mShowHistory = savedInstanceState.getBoolean("mShowHistory");
        mUserEntryList = UserEntryList.getEchoListObjectFromJSON(savedInstanceState.getString("userEntryList"));
        super.onRestoreInstanceState(savedInstanceState);
    }
}
package com.example.textechoapp.model;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;

public class UserEntryList
{
    private final ArrayList<String> mUserEntriesList;

    public UserEntryList (){
        this.mUserEntriesList = new ArrayList<>();
    }

    public void addEntryToList (String str){
        this.mUserEntriesList.add(str);
    }

    public ArrayList<String> getUserEntriesListAsList (){
        return new ArrayList<> (this.mUserEntriesList);
    }

    public String getUserEntriesListAsString (){
        StringBuilder sbEntries = new StringBuilder();

        for (String entry : mUserEntriesList){
            sbEntries.append(entry).append("\n");
        }

        return sbEntries.toString();
    }

    public String getLastUserEntry ()
    {
        return mUserEntriesList.get(mUserEntriesList.size ()-1);
    }

    public void clearUserEntries()
    {
        mUserEntriesList.clear ();
    }

    @NonNull @Override public String toString ()
    {
        return "EchoList{" + "User Entries List: " + mUserEntriesList + '}';
    }

    public String getJSONStringFromThis()
    {
        return UserEntryList.getJSONStringFromEchoListObject (this);
    }

    public static UserEntryList getEchoListObjectFromJSON (String json)
    {
        Gson gson = new Gson ();
        return gson.fromJson (json, UserEntryList.class);
    }

    public static String getJSONStringFromEchoListObject (UserEntryList userEntryList)
    {
        Gson gson = new Gson ();
        return gson.toJson (userEntryList);
    }
}

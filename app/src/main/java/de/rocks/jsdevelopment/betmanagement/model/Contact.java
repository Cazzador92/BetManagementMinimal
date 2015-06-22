package de.rocks.jsdevelopment.betmanagement.model;

import java.util.ArrayList;

/**
 * Created by jf on 17.06.15.
 */
public class Contact {

    protected String mName;
    protected String mId;
    protected String mRawId;
    protected ArrayList<Email> mEmails;
    //Picture later.

    public Contact(){
        this.mName = "";
        this.mId = "";
        this.mRawId = "";
        this.mEmails = new ArrayList<>();
    }

    public Contact(String mName) {
        this.mName = mName;
        this.mId = "";
        this.mRawId = "";
        this.mEmails = new ArrayList<>();
    }

    public Contact(String mName, String mId) {
        this.mName = mName;
        this.mId = mId;
        this.mRawId = "";
        this.mEmails = new ArrayList<>();
    }

    public Contact(String mName, String mId, String mRawId) {
        this.mName = mName;
        this.mId = mId;
        this.mRawId = mRawId;
        this.mEmails = new ArrayList<>();
    }

// later
    public Contact(String mName, String mId, String mRawId, ArrayList<Email> mEmails) {
        this.mName = mName;
        this.mId = mId;
        this.mRawId = mRawId;
        this.mEmails = mEmails;
    }


    public String getName() {
        return mName;
    }

    public void setName(String Name) {
        this.mName = Name;
    }

    public String getId() {
        return mId;
    }

    public void setId(String Id) {
        this.mId = Id;
    }

    public String getRawId() {
        return mRawId;
    }

    public void setRawId(String RawId) {
        this.mRawId = RawId;
    }

    public ArrayList<Email> getEmails() {
        return mEmails;
    }

    public void setEmails(ArrayList<Email> Emails) {
        this.mEmails = Emails;
    }

    public Email getEmail() {
        return mEmails.get(0);
    }

    public void addEmail(Email Email) {
        this.mEmails.add(Email);
    }
}

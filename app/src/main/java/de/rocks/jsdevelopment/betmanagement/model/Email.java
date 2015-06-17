package de.rocks.jsdevelopment.betmanagement.model;

/**
 * Created by jf on 17.06.15.
 */
public class Email {
    private static final String DEFAULT_EMAIL_TYPE = "Default";

    protected String mEmail, mEmailType;

    public Email(){
        mEmail = "";
        mEmailType = "";
    }
    public Email(String Email){
        mEmail = Email;
        mEmailType = DEFAULT_EMAIL_TYPE;
    }
    public Email(String Email, String EmailType){
        mEmail = Email;
        if (EmailType != "") {
            mEmailType = EmailType;
        }else{
            mEmailType = DEFAULT_EMAIL_TYPE;
        }
    }

    public String getEmail(){
        return mEmail;
    }

    public String getEmailType(){
        return mEmailType;
    }
}

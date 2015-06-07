package de.rocks.jsdevelopment.betmanagement;

import android.content.Intent;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sebastian on 04.06.2015.
 */
public class BetItem implements Serializable{

    public Integer Nr;
    public String ShortDescription;
    public String FullDescription;
    public Date StartDate;
    public Date EndDate;

    public BetItem() {
        Nr = 0;
        ShortDescription = "";
        FullDescription = "";
        StartDate = new Date();
        EndDate = new Date();
    }

    public BetItem(Integer Nr, String ShortDescription, String FullDescription, Date StartDate, Date EndDate) {
        super();

        this.Nr = Nr;
        this.ShortDescription = ShortDescription;
        this.FullDescription = FullDescription;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
    }

    @Override //vllt unnötig.
    public String toString() {
        return this.Nr + ". " + this.ShortDescription;
    }

    public String getPeriod()
    {

        //return this.StartDate.getDate() + " - " + this.EndDate.getDate();
        return this.StartDate.getDay() + "." + this.StartDate.getMonth() + "." + this.StartDate.getYear() + " - " +
                this.EndDate.getDay() + "." + this.EndDate.getMonth() + "." + this.EndDate.getYear();
    }
}


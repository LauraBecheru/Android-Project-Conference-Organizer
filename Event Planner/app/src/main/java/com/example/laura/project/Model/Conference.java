package com.example.laura.project.Model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Laura on 05.11.2017.
 */
/*RatingBar*/
public class Conference implements Parcelable {
    User user;
    String name;//textBox
    String organizer;//textBox
    String relatedField;//Spinner
    int duration;//seek bar
    String ConfDate;//date
    //boolean fee;//switch button
    float feeAmount;// textBox
    String noSpeakers;//radio button
    String location;

    public static final String UserEmail = "";

    public Conference(String name, float feeAmount) {
        this.name = name;
        this.feeAmount = feeAmount;
    }

    public Conference(String name, String organizer, String relatedField, int duration,
                      String confDate, float feeAmount, String noSpeakers, String location) {
        this.name = name;
        this.organizer = organizer;
        this.relatedField = relatedField;
        this.duration = duration;
        ConfDate = confDate;
        this.feeAmount = feeAmount;
        this.noSpeakers = noSpeakers;
        this.location = location;
    }

    private Conference(Parcel in) {
        name =in.readString() ;
        organizer =in.readString() ;
        relatedField = in.readString();
        duration = in.readInt();
        ConfDate =in.readString();
        feeAmount = in.readFloat();
        noSpeakers =  in.readString();
        location=in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getRelatedField() {
        return relatedField;
    }

    public void setRelatedField(String relatedField) {
        this.relatedField = relatedField;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getConfDate() {
        return ConfDate;
    }

    public void setConfDate(String confDate) {
        ConfDate = confDate;
    }

   /* public boolean isFee() {
        return fee;
    }

    public void setFee(boolean fee) {
        this.fee = fee;
    }*/

    public float getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(float feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getNoSpeakers() {
        return noSpeakers;
    }

    public void setNoSpeakers(String noSpeakers) {
        this.noSpeakers = noSpeakers;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Conference:\n   Name = " + name +
                "\n   Organizer = " + organizer +
                "\n   Field =" + relatedField  +
                "\n   Duration = " + duration +
                "\n   Date = " + ConfDate  +
                "\n   Fee = " + feeAmount +
                "\n   Speakers = " + noSpeakers +
                "\n   Location: \n" + location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(organizer);
        parcel.writeString(relatedField);
        parcel.writeInt(duration);
        parcel.writeString(ConfDate);
        parcel.writeFloat(feeAmount);
        parcel.writeString(noSpeakers);
        parcel.writeString(location);
    }

    public static final Parcelable.Creator<Conference> CREATOR
            = new Parcelable.Creator<Conference>() {
        public Conference createFromParcel(Parcel in) {
            return new Conference(in);
        }

        public Conference[] newArray(int size) {
            return new Conference[size];
        }
    };


}

package com.example.laura.project.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Laura on 06.11.2017.
 */

public class Location implements Parcelable
{
    String locName;
    String room;
    String seats;

    public Location(String locName, String room, String seats) {
        this.locName = locName;
        this.room = room;
        this.seats = seats;
    }

    protected Location(Parcel in) {
        locName = in.readString();
        room = in.readString();
        seats = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return  "Name='" + locName +
                "Room='" + room  +
                "Seats='" + seats;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(locName);
        parcel.writeString(room);
        parcel.writeString(seats);
    }
}

package edu.upc.dsa.minim2example.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RelTemes implements Parcelable {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("elements")
    @Expose
    private String elements;

    protected RelTemes(Parcel in) {
        if (in.readByte() == 0) {
            count = null;
        } else {
            count = in.readInt();
        }
        elements = in.readString();
    }

    public static final Creator<RelTemes> CREATOR = new Creator<RelTemes>() {
        @Override
        public RelTemes createFromParcel(Parcel in) {
            return new RelTemes(in);
        }

        @Override
        public RelTemes[] newArray(int size) {
            return new RelTemes[size];
        }
    };

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getElements() {
        return elements;
    }

    public void setElements(String elements) {
        this.elements = elements;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (count == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(count);
        }
        parcel.writeString(elements);
    }
}

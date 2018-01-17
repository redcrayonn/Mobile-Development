package nl.inholland.imready.model.blocks;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import nl.inholland.imready.model.EntityModel;
import nl.inholland.imready.model.user.User;

public class Feedback extends EntityModel implements Parcelable {
    public static final Creator<Feedback> CREATOR = new Creator<Feedback>() {
        @Override
        public Feedback createFromParcel(Parcel in) {
            return new Feedback(in);
        }

        @Override
        public Feedback[] newArray(int size) {
            return new Feedback[size];
        }
    };
    @SerializedName("Caregiver")
    private User caregiver;
    @SerializedName("Activity")
    private PersonalActivity activity;
    @SerializedName("Sent")
    private Date sent;
    @SerializedName("Content")
    private String content;

    public Feedback(Parcel in) {
        super(in);
        content = in.readString();
    }

    public Feedback(String content){this.content = content;}

    public Date getSent() {
        return sent;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getCaregiver() {
        return caregiver;
    }

    public void setCaregiver(User caregiver) {
        this.caregiver = caregiver;
    }

    public PersonalActivity getActivity() {
        return activity;
    }

    public void setActivity(PersonalActivity activity) {
        this.activity = activity;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(content);
    }
}

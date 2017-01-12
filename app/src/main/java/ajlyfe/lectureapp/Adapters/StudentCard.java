/*
 * Copyright (c) 2017 RecoLecture.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ajlyfe.lectureapp.Adapters;

import android.os.Parcel;
import android.os.Parcelable;

public class StudentCard implements Parcelable {

    private String name;
    private String dateJoined;
    private String email;

    public StudentCard(String name, String dateJoined, String email) {
        this.name = name;
        this.dateJoined = dateJoined;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    /**
     *
     *
     *
     * PARCELABLE SHIT
     *
     *
     *
     *
     */

    private StudentCard(Parcel in) {
        name = in.readString();
        dateJoined = in.readString();
        email = in.readString();
    }

    public void writeToParcel(Parcel out, int flags) {
        // Again this order must match the Question(Parcel) constructor
        out.writeString(name);
        out.writeString(dateJoined);
        out.writeString(email);
        // Again continue doing this for the rest of your member data
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Just cut and paste this for now
    public static final Parcelable.Creator<StudentCard> CREATOR = new Parcelable.Creator<StudentCard>() {
        public StudentCard createFromParcel(Parcel in) {
            return new StudentCard(in);
        }

        public StudentCard[] newArray(int size) {
            return new StudentCard[size];
        }
    };
}
package fr.melanoxy.mareu.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Reunion {

    private final long id;
    @NonNull
    private final String date;
    @Nullable
    private final String place;
     @Nullable
    private final String subject;
    @Nullable
    private final String people;



        // constructor to initialize
        // the variables
        public Reunion(
                long id,
                @NonNull String date,
                @Nullable String place,
                @Nullable String subject,
                @Nullable String people
        ){
            this.id = id;
            this.date = date;
            this.place = place;
            this.subject = subject;
            this.people = people;
        }

        // getters

        public long getId() {
        return id;
    }

        @NonNull
        public String getDate() {
        return date;
        }

        @Nullable
        public String getPlace() {
            return place;
        }

        @Nullable
        public String getSubject() {
            return subject;
        }


        @Nullable
        public String getPeople() {
            return people;
        }


    }

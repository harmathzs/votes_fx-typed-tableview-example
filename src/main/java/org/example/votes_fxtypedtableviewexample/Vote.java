package org.example.votes_fxtypedtableviewexample;

import java.time.LocalDate;

public class Vote {
    public String id;
    public String firstName;
    public String lastName;
    public LocalDate birthday;
    public String address;
    public String votedParty;

    public Vote(String id, String firstName, String lastName, String birthday, String address, String votedParty) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = LocalDate.parse(birthday);
        this.address = address;
        this.votedParty = votedParty;
    }
}

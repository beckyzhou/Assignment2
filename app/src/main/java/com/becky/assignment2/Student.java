package com.becky.assignment2;

/**
 * Created by Becky on 2015-04-01.
 */
public class Student {

    private long id;
    private String first;

    public long getId()
    {
        return (id);
    }

    public void setId(final long i)
    {
        id = i;
    }

    public String getFirstName()
    {
        return (first);
    }

    public void setComment(final String c)
    {
        first = c;
    }

    // Will be used by the ArrayAdapter in the ListView
    // WHICH IS A HORRIBLE DESIGN!!!!!
    // toString should ONLY ever be used for debugging by developers!
    // Keep that in mind when you develop code!!!!!!!!!!!
    @Override
    public String toString()
    {
        return first;
    }

}

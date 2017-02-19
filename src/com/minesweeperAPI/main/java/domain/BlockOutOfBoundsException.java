package com.minesweeperAPI.main.java.domain;

/**
 * Created by Pablo on 19/2/2017.
 */
//Checked exception
public class BlockOutOfBoundsException extends Exception{

    public BlockOutOfBoundsException() {}

    //Constructor that accepts a message
    public BlockOutOfBoundsException(String message)
    {
        super(message);
    }
}

package com.minesweeperAPI.java.domain;

import java.util.UUID;

/**
 * Created by Pablo on 18/2/2017.
 */
public class Block {
    String id;
    Integer row;
    Integer column;
    Integer value;
    Boolean isMine;
    Boolean flagged;
    Boolean flipped;

    public Block(Integer row, Integer column) {
        
        this.id = UUID.randomUUID().toString();
        this.row = row;
        this.column = column;
        this.flagged = false;
        this.flipped = false;
        this.value = 0;
    }

    public String getId() {
        return id;
    }

    public Integer getRow() {
        return row;
    }
    
    public Integer getColumn() {
        return column;
    }

    public Integer getValue() {
        return value;
    }

    public Boolean getFlagged() {
        return flagged;
    }

    public void setFlagged(Boolean flagged) {
        this.flagged = flagged;
    }

    public Boolean getFlipped() {
        return flipped;
    }

    public void setFlipped(Boolean flipped) {
        this.flipped = flipped;
    }

    public Boolean getIsMine() {
        return isMine;
    }

    public void setIsMine(Boolean isMine) {
        this.isMine = isMine;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}

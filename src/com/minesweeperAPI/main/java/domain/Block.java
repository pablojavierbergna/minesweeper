package com.minesweeperAPI.main.java.domain;

import java.util.UUID;

/**
 * Created by Pablo on 18/2/2017.
 */
public class Block {
    private String uuid;
    private Integer row;
    private Integer column;
    private Integer value;
    private Boolean isMine;
    private Boolean flagged;
    private Boolean flipped;

    public Block(final Integer row, final Integer column) {
        
        this.uuid = UUID.randomUUID().toString();
        this.row = row;
        this.column = column;
        this.flagged = false;
        this.flipped = false;
        this.value = 0;
    }

    public String getUuid() {
        return uuid;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Block)) return false;

        Block block = (Block) o;

        return getUuid().equals(block.getUuid());
    }

    @Override
    public int hashCode() {
        return getUuid().hashCode();
    }
}

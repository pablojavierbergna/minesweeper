package com.minesweeperAPI.main.java.controllers;

/**
 * Created by Pablo on 19/2/2017.
 */
public class FlipBlockResponse {

    private String gameUuid;
    private String result;
    private Integer value;
    private Integer row;
    private Integer column;

    FlipBlockResponse(final String gameUuid, final String result, final Integer value,
                      final Integer row, final Integer column) {
        this.gameUuid = gameUuid;
        this.result = result;
        this.value = value;
        this.row = row;
        this.column = column;
    }

    public String getGameUuid() {
        return gameUuid;
    }

    public void setGameUuid(String gameUuid) {
        this.gameUuid = gameUuid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }
}

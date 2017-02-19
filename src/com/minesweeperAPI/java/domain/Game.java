package com.minesweeperAPI.java.domain;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import org.apache.commons.lang3.Validate;


/**
 * Created by Pablo on 18/2/2017.
 */
public class Game {

    static final Integer DEFAULT_ROWS = 9;
    static final Integer DEFAULT_COLUMNS = 9;
    static final Integer DEFAULT_MINES = 10;

    String uuid;
    Block[][] blocks;
    Boolean finished;
    Date initialized;

    //Configurable
    Integer rows;
    Integer columns;
    Integer mines;

    public Game() {
        this(DEFAULT_ROWS, DEFAULT_COLUMNS, DEFAULT_MINES);
    }

    public Game(Integer rows, Integer columns, Integer mines) {

        Validate.notNull(rows, "Game cannot have null rows");
        Validate.notNull(columns, "Game cannot have null columns");
        Validate.notNull(mines, "Game cannot have null mines");

        Validate.isTrue(rows != 0, "Game rows cannot be zero nor negative");
        Validate.isTrue(columns != 0, "Game columns cannot be zero nor negative");
        Validate.isTrue(mines != 0, "Game mines cannot be zero nor negative");

        this.rows = rows;
        this.columns = columns;
        this.mines = mines;

        uuid = UUID.randomUUID().toString();
        finished = false;
        initialized = new Date();

        blocks = new Block[this.rows][this.columns];

        //initialize blocks, setting mines and values accordingly
        for (int i = 1 ; i <= this.rows ; i++) {
            for (int j = 1 ; i <= this.columns ; j++) {
                blocks[i][j] = new Block(i,j);
            }
        }

        Random randomGen = new Random();
        int minesGenerated = 0;
        do {
            int randomRow = randomGen.nextInt(this.rows) + 1;
            int randomCol = randomGen.nextInt(this.columns) + 1;

            if (blocks[randomRow][randomCol].getFlagged() == false) {
                blocks[randomRow][randomCol].setFlagged(true);
                minesGenerated++;
            }
        } while (minesGenerated < this.mines);

    }



}

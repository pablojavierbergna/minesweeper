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

    public Game(Integer someRows, Integer someColumns, Integer someMines) {

        Validate.notNull(someRows, "Game cannot have null rows");
        Validate.notNull(someColumns, "Game cannot have null columns");
        Validate.notNull(someMines, "Game cannot have null mines");

        Validate.isTrue(someRows != 0, "Game rows cannot be zero nor negative");
        Validate.isTrue(someColumns != 0, "Game columns cannot be zero nor negative");
        Validate.isTrue(someMines != 0, "Game mines cannot be zero nor negative");

        this.rows = someRows;
        this.columns = someColumns;
        this.mines = someMines;

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

            if (blocks[randomRow][randomCol].getIsMine() == false) {
                blocks[randomRow][randomCol].setIsMine(true);
                minesGenerated++;
            }
        } while (minesGenerated < this.mines);

        //calculate values for blocks depending on surrounding mines
        for (int i = 1 ; i <= this.rows ; i++) {
            for (int j = 1 ; i <= this.columns ; j++) {
                int surroundingMines = countSurroundingMines(blocks[i][j]);
                blocks[i][j].setValue(surroundingMines);
            }
        }
    }

    /**
     * Method used to flag a block as a mine, done by the user
     * @param aRow
     * @param aColumn
     */
    public void flagBlock(Integer aRow, Integer aColumn) {
        if (!isValidCoordinate(aRow, aColumn)) {
            throw new IllegalArgumentException("Trying to flag a block out of game borders");
        }

        if (blocks[aRow][aColumn].getFlagged() == true) {
            blocks[aRow][aColumn].setFlagged(false);
        } else {
            blocks[aRow][aColumn].setFlagged(true);
        }
    }


    /**
     * Validates that a coordinate is in bounds
     * @param aRow
     * @param aColumn
     * @return
     */
    private Boolean isValidCoordinate(Integer aRow, Integer aColumn) {
        if (aRow >= 1 && aRow <= this.rows && aColumn >= 1 && aColumn <= this.columns) {
            return true;
        } else {
            return false;
        }
    }

    //Methods for calculating surrounding mines
    private int countSurroundingMines(Block aBlock) {
        int mines = countMinesUpperLeft(aBlock);
        mines += countMinesUp(aBlock);
        mines += countMinesUpperRight(aBlock);
        mines += countMinesLeft(aBlock);
        mines += countMinesRight(aBlock);
        mines += countMinesDownLeft(aBlock);
        mines += countMinesDown(aBlock);
        mines += countMinesDownRight(aBlock);
        return mines;
    }

    private int countMinesLeft(Block aBlock) {
        if (!isValidCoordinate(aBlock.getRow(), aBlock.getColumn() - 1)) return 0;
        if (blocks[aBlock.getRow()][aBlock.getColumn() - 1].getIsMine() == true) {
            return 1;
        } else {
            return 0;
        }
    }

    private int countMinesRight(Block aBlock) {
        if (!isValidCoordinate(aBlock.getRow(), aBlock.getColumn() + 1)) return 0;
        if (blocks[aBlock.getRow()][aBlock.getColumn() + 1].getIsMine() == true) {
            return 1;
        } else {
            return 0;
        }
    }

    private int countMinesUp(Block aBlock) {
        if (!isValidCoordinate(aBlock.getRow() - 1, aBlock.getColumn())) return 0;
        if (blocks[aBlock.getRow() - 1][aBlock.getColumn()].getIsMine() == true) {
            return 1;
        } else {
            return 0;
        }
    }

    private int countMinesDown(Block aBlock) {
        if (!isValidCoordinate(aBlock.getRow() + 1, aBlock.getColumn())) return 0;
        if (blocks[aBlock.getRow() + 1][aBlock.getColumn()].getIsMine() == true) {
            return 1;
        } else {
            return 0;
        }
    }

    private int countMinesUpperLeft(Block aBlock) {
        if (!isValidCoordinate(aBlock.getRow() - 1, aBlock.getColumn() - 1)) return 0;
        if (blocks[aBlock.getRow() - 1][aBlock.getColumn() - 1].getIsMine() == true) {
            return 1;
        } else {
            return 0;
        }
    }

    private int countMinesUpperRight(Block aBlock) {
        if (!isValidCoordinate(aBlock.getRow() - 1, aBlock.getColumn() + 1)) return 0;
        if (blocks[aBlock.getRow() - 1][aBlock.getColumn() + 1].getIsMine() == true) {
            return 1;
        } else {
            return 0;
        }
    }

    private int countMinesDownLeft(Block aBlock) {
        if (!isValidCoordinate(aBlock.getRow() + 1, aBlock.getColumn() - 1)) return 0;
        if (blocks[aBlock.getRow() + 1][aBlock.getColumn() - 1].getIsMine() == true) {
            return 1;
        } else {
            return 0;
        }
    }

    private int countMinesDownRight(Block aBlock) {
        if (!isValidCoordinate(aBlock.getRow() + 1, aBlock.getColumn() + 1)) return 0;
        if (blocks[aBlock.getRow() + 1][aBlock.getColumn() + 1].getIsMine() == true) {
            return 1;
        } else {
            return 0;
        }
    }
}

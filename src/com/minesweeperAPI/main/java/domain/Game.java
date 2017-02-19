package com.minesweeperAPI.main.java.domain;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import org.apache.commons.lang3.Validate;


/**
 * Created by Pablo on 18/2/2017.
 */
public class Game {
    public static final Integer END_OF_GAME_VICTORY_CODE = 100;
    public static final Integer END_OF_GAME_DEFEAT_CODE = 101;

    private static final Integer DEFAULT_ROWS = 9;
    private static final Integer DEFAULT_COLUMNS = 9;
    private static final Integer DEFAULT_MINES = 10;

    private String uuid;
    private Block[][] blocks;
    private Boolean finished;
    private Date initialized;
    private Integer remainingBlocks;

    //Configurable
    private Integer rows;
    private Integer columns;
    private Integer mines;

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

        rows = someRows;
        columns = someColumns;
        mines = someMines;
        remainingBlocks = rows*columns;
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

            if (!blocks[randomRow][randomCol].getIsMine()) {
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
     * Method to flip a block and tell if a mine was inside or not,
     * chaining the reaction to flip nearby blocks with no mines surrounding
     */
    public int flipBlock(Integer aRow, Integer aColumn) {
        if (!isValidCoordinate(aRow, aColumn)) {
            throw new IllegalArgumentException("Trying to flip a block out of game borders");
        }

        if (blocks[aRow][aColumn].getIsMine() == true) {
            return END_OF_GAME_DEFEAT_CODE;
        } else {
            blocks[aRow][aColumn].setFlipped(true);
            remainingBlocks--;
            flipNeightbours(aRow, aColumn);
        }

        return isEndOfGame();
    }

    /**
     * Method to flip nearby blocks that are not mines
     * @param aRow
     * @param aColumn
     */
    private void flipNeightbours(Integer aRow, Integer aColumn) {
        if (blocks[aRow][aColumn].getIsMine()) {
            return;
        }
        if (!blocks[aRow][aColumn].getFlipped()) {
            blocks[aRow][aColumn].setFlipped(true);
            remainingBlocks--;
        }
        if (isValidCoordinate(aRow - 1, aColumn - 1)
                && countMinesUpperLeft(blocks[aRow][aColumn]) == 0) {
            flipNeightbours(aRow - 1, aColumn - 1);
        }
        if (isValidCoordinate(aRow - 1, aColumn)
                && countMinesUp(blocks[aRow][aColumn]) == 0) {
            flipNeightbours(aRow - 1, aColumn);
        }
        if (isValidCoordinate(aRow - 1, aColumn + 1)
                && countMinesUpperRight(blocks[aRow][aColumn]) == 0) {
            flipNeightbours(aRow - 1, aColumn + 1);
        }
        if (isValidCoordinate(aRow, aColumn - 1)
                && countMinesLeft(blocks[aRow][aColumn]) == 0) {
            flipNeightbours(aRow, aColumn - 1);
        }
        if (isValidCoordinate(aRow, aColumn + 1)
                && countMinesRight(blocks[aRow][aColumn]) == 0) {
            flipNeightbours(aRow, aColumn + 1);
        }
        if (isValidCoordinate(aRow + 1, aColumn - 1)
                && countMinesDownLeft(blocks[aRow][aColumn]) == 0) {
            flipNeightbours(aRow + 1, aColumn - 1);
        }
        if (isValidCoordinate(aRow + 1, aColumn)
                && countMinesDown(blocks[aRow][aColumn]) == 0) {
            flipNeightbours(aRow + 1, aColumn);
        }
        if (countMinesDownRight(blocks[aRow][aColumn]) == 0) {
            flipNeightbours(aRow + 1, aColumn + 1);
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
     * Method to tell the end of the game
     * @return END_OF_GAME_VICTORY_CODE in case of game ended, else 0
     */
    private int isEndOfGame() {
        if (remainingBlocks == mines) {
            return END_OF_GAME_VICTORY_CODE;
        }
        return 0;
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

    /**
     * Similar to isValidCoordinate method but not returning a value, instead throws an exception.
     * @param aRow
     * @param aColumn
     * @throws BlockOutOfBoundsException
     */
    private void ValidateCoordinate(Integer aRow, Integer aColumn) throws BlockOutOfBoundsException {
        if (aRow < 1 || aRow > this.rows || aColumn < 1 || aColumn > this.columns) {
            throw new BlockOutOfBoundsException();
        }
    }

    //**************************************************************************************************************

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

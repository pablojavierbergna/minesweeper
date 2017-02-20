# minesweeper API

This API consists in various endpoints aiming to play the Minesweeper game.

The following URL's are used to play the game:

*/api/v1/games
Used to create a new Minesweeper Game

**Method: POST

**URL Params:

***Rows (number of rows for the game)
***Columns (number of columns for the game)
***Mines (number of mines to be planted)

**Responses:

***Success (200)
***Content: uuid of the game created

***Notes: If no parameters are provided then the game will be created with default parameters (9 rows, 9 columns and 10 mines)


*/api/v1/games/{aGameUuid}/flip
Used to flip a specific block (using the block's row and column for orientation) and retrieve the value according to the result.
The possibles results are:
**The game is finished and the player won because that was the last block to be flipped and it wasn't a mine.
**The game is finished and the player lost because that block was a mine.
**The game goes on and the value for the quantity of mines surrounding that block is retrieved by this method.

**Method: POST

**URL Params:

***Row (the row to the flipped)
***Column (the column to be flipped)
***aGameUuid (the game uuid to be used to flip a block)

**Responses

***Bad Request (400) In case the row and column provided are out the range of the board
***Not Found (404) In case the game uuid does not correspond to any open game.
***Success (200) In case the flip action was performed, retrieving a response like:

{
    gameUuid: String with the uuid
    result: String with possible values:
        "Flipped": Block flipped successfully.
        "Victory": The block has been flipped and the player has won the game.
        "Defeated": The block has been flipped and the player has lost the game (the block was a mine).
    values: String with the number of mines surrounding the block.
    row: The row corresponding to the block being flipped.
    column: The column corresponding to the block being flipped.
}


*/api/v1/games/{aGameUuid}/flag
Used to flag a specific block (using the block's row and column for orientation).

**Method: POST

**URL Params:

***Row (the row to the flagged)
***Column (the column to be flagged)
***aGameUuid (the game uuid to be used to flag a block)

**Responses

***Bad Request (400) In case the row and column provided are out the range of the board
***Not Found (404) In case the game uuid does not correspond to any open game.
***Success (200) In case the flag action was performed successfully.
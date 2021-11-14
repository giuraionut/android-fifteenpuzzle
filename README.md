# Fifteen Puzzle game
<p align = "center">
<img src = "https://img.shields.io/badge/KOTLIN-purple">
<img src = "https://img.shields.io/badge/ANDROID STUDIO-green">
</p>

## Fifteen Puzzle made with Android Studio and Kotlin

* Game needs some styling. Currently it works but it looks basic.

![Image](https://github.com/giuraionut/android-fifteenpuzzle/blob/main/presentation/img.png)

### Details

* This application is a simple [Fifteen Game](https://en.wikipedia.org/wiki/15_puzzle).
* The "board" is made from 16 buttons. The "empty space" is a white `button with no text`.
* The pieces that player can move are buttons with text from `1->15`.

* When a player clicks a button, we check:
    * All it's neighbours on 4 directions: `North, South, East and West`.
     * On a matrix those positions would be:
        * x + (1,0)
        * x - (0,1)
        * y + (1,0)
        * y - (0,1)
    * We ignore those who go out of bounds ( bigger than matrix size or negative values )
    * If we find a neighbour that has `no text` this is our empty button, and the current button will switch places with it.
* All buttons positions are saved in a `List` so we can check if they are in order. If the list is sorted, the game is over.

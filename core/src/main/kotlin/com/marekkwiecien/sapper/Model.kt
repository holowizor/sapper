package com.marekkwiecien.sapper

interface WithCoordinates {
    val x: Int
    val y: Int
}

data class Point(override val x: Int, override val y: Int) : WithCoordinates
data class Field(override val x: Int, override val y: Int, val mine: Boolean, val neighbourMines: Int, var visited: Boolean = false) : WithCoordinates
typealias Mine = Point



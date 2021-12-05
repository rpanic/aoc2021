package utils

import java.lang.Math.abs
import utils.Directions.*

data class Vector2D(
    var x: Int = 0,
    var y: Int = 0,
) {
    operator fun plus(other: Vector2D) = Vector2D(x + other.x, y + other.y)
    operator fun plusAssign(other: Vector2D) = run { x += other.x; y += other.y }
    operator fun minus(other: Vector2D) = Vector2D(x - other.x, y - other.y)
    operator fun minusAssign(other: Vector2D) = run { x -= other.x; y -= other.y }
    operator fun unaryMinus() = Vector2D(-x, -y)
    operator fun times(other: Int) = Vector2D(x * other, y * other)
    operator fun timesAssign(other: Int) = run { x *= other; y *= other }

    fun length2() = x * x + y * y
    fun manhattan() = abs(x) + abs(y)
    fun toList() = listOf(x, y)
}

enum class Directions(val step: Vector2D) {
    U(0, -1),
    D(0, 1),
    L(-1, 0),
    R(1, 0),
    UL(-1, -1),
    UR(1, -1),
    DL(-1, 1),
    DR(1, 1);

    constructor(x: Int, y: Int) : this(Vector2D(x, y))
}

val Neighbors4 = listOf(U, D, L, R)
val Neighbors8 = listOf(UL, U, UR, L, R, DL, D, DR)
val CCW = mapOf(U to L, L to D, D to R, R to U)
val CW = mapOf(U to R, R to D, D to L, L to U)
val REV = mapOf(U to D, D to U, L to R, R to L)
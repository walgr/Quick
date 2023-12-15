package com.wpf.app.quickutil.data

class Quadruple<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val four: D,
) {

    override fun toString(): String {
        return "$first, $second, $third, $four"
    }
}
package com.ur4n0.castellari.util

fun thisCanBeInteger(input: String): Boolean{
    return try{
        input.toInt()
        true
    }catch(Error: NumberFormatException){
        false
    }
}

fun thisCanBeDouble(input: String): Boolean{
    return try{
        input.toDouble()
        true
    }catch(Error: NumberFormatException){
        false
    }
}

fun convertToMonetaryCase(value: Double): String{
    return String.format("%.2f", value)
}
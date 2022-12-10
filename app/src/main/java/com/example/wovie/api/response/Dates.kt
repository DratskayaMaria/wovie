package com.example.wovie.api.response

class Dates {
    var maximum: String? = null
    var minimum: String? = null
    override fun toString(): String {
        return "Dates{" +
                "maximum='" + maximum + '\'' +
                ", minimum='" + minimum + '\'' +
                '}'
    }
}
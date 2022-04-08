//@file:JsExport

package com.example

import kotlin.js.JsExport



@DataClass("TripModel")
@JsExport
interface Trip{
    val name:String
}
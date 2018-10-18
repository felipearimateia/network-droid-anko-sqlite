package org.androidbh.networkdroid.database

interface RowParser<T> {
    fun parseRow(columns: Array<Any>): T
}
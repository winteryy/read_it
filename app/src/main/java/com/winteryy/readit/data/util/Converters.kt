package com.winteryy.readit.data.util

import com.winteryy.readit.model.BookSaveStatus

object BookSaveStatusConverter {
    fun toNum(bookSaveStatus: BookSaveStatus): Int {
        return when(bookSaveStatus) {
            BookSaveStatus.NONE -> 0
            BookSaveStatus.WISH -> 1
            BookSaveStatus.READING -> 2
            BookSaveStatus.RATED -> 3
        }
    }

    fun toStatus(num: Int): BookSaveStatus {
        return when(num) {
            0 -> BookSaveStatus.NONE
            1 -> BookSaveStatus.WISH
            2 -> BookSaveStatus.READING
            3 -> BookSaveStatus.RATED
            else -> throw IllegalArgumentException("Invalid Argument(Range is 0..3)")
        }
    }
}
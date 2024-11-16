package com.winteryy.readit.model

data class Section(
    val sectionType: SectionType,
    val bookList: List<Book>,
    val emptyMsg: String? = null
)

sealed interface SectionType {
    val id: Long
    val title: String
    val showRating: Boolean

    object READING: SectionType {
        override val id: Long
            get() = 1L
        override val title: String
            get() = "읽는 중인 책"
        override val showRating: Boolean
            get() = false
    }
    object WISH: SectionType {
        override val id: Long
            get() = 2L
        override val title: String
            get() = "읽고 싶은 책"
        override val showRating: Boolean
            get() = false
    }
    object RATED: SectionType {
        override val id: Long
            get() = 3L
        override val title: String
            get() = "평가한 책"
        override val showRating: Boolean
            get() = true
    }
}


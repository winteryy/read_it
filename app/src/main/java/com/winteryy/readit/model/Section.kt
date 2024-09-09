package com.winteryy.readit.model

data class Section(
    val sectionType: SectionType,
    val bookList: List<Book>
)

sealed interface SectionType {
    val id: Long
    val title: String

    object READING: SectionType {
        override val id: Long
            get() = 1L
        override val title: String
            get() = "읽는 중인 책"
    }
    object WISH: SectionType {
        override val id: Long
            get() = 2L
        override val title: String
            get() = "읽고 싶은 책"
    }
    object RATED: SectionType {
        override val id: Long
            get() = 3L
        override val title: String
            get() = "평가한 책"
    }
}


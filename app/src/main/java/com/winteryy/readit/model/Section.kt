package com.winteryy.readit.model

data class Section(
    val sectionType: SectionType,
    val bookList: List<Book>
)

sealed interface SectionType {
    val title: String

    object READING: SectionType {
        override val title: String
            get() = "읽는 중인 책"
    }
    object WISH: SectionType {
        override val title: String
            get() = "읽을 예정인 책"
    }
    object RATED: SectionType {
        override val title: String
            get() = "평가한 책"
    }
}


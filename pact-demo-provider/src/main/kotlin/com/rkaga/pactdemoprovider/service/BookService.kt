package com.rkaga.pactdemoprovider.service

import com.rkaga.pactdemoprovider.domain.Book
import com.rkaga.pactdemoprovider.domain.Price
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class BookService {

    val bookList = listOf(
            Book(
                    id = 1,
                    name = "sample book a",
                    price = Price(
                            taxExclude = 1000
                    )
            ),
            Book(
                    id = 2,
                    name = "sample book b",
                    price = Price(
                            taxExclude = 500
                    )
            )
    )

    fun getAllBooks() : List<Book> {
        return bookList
    }

    fun getBook(bookId: Int) : Book {
        return bookList.firstOrNull { it.id == bookId } ?: throw Exception("Not Found")
    }
}

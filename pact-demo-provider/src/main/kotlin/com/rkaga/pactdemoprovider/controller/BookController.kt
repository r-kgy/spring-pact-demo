package com.rkaga.pactdemoprovider.controller

import com.rkaga.pactdemoprovider.domain.Book
import com.rkaga.pactdemoprovider.service.BookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(
        private val bookService: BookService
) {
    @GetMapping("/books")
    fun getBooks() : List<Book> {
        return bookService.getAllBooks()
    }

    @GetMapping("/book/{bookId}")
    fun getBook(
            @PathVariable(name = "bookId") bookId: Int
    ) : Book {
        return bookService.getBook(bookId)
    }
}

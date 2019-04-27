package mirea.petshop.model

import java.time.LocalDateTime

class Token {
    private val userId: Int = 0
    private val name: String? = null
    private val issued: LocalDateTime? = LocalDateTime.now()
    private val expires: LocalDateTime? = null
}
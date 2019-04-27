package mirea.petshop.rest

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cart")
class CartController {
    @GetMapping
    fun getCartContents() {

    }

    @PostMapping
    fun checkout() {

    }

    @DeleteMapping
    fun removeItem() {

    }

    @PutMapping
    fun updateItem() {

    }
}
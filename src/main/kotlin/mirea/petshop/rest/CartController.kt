package mirea.petshop.rest

import mirea.petshop.model.Cart
import mirea.petshop.service.AuthService
import mirea.petshop.service.CartService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cart")
@PreAuthorize("@authService.hasRole({'User'}, #token)")
class CartController @Autowired constructor(val cartService: CartService, val authService: AuthService) {

    @GetMapping
    fun getCartContents(@RequestHeader("auth") token: Token): ResponseEntity<Cart> {
        val userID = authService.getUserID(token) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        return ResponseEntity(cartService.getCart(userID), HttpStatus.OK)
    }

    @GetMapping("/all")
    @PreAuthorize("@authService.hasRole({'Admin'}, #token)")
    fun getAll(@RequestHeader("auth") token: Token): ResponseEntity<Array<Cart>> {
        return ResponseEntity(cartService.getAll(), HttpStatus.OK)
    }

    @PostMapping
    fun checkout(@RequestHeader("auth") token: Token): ResponseEntity<String> {
        val userID = authService.getUserID(token) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        return if (cartService.checkout(userID))
            ResponseEntity(HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @DeleteMapping
    fun removeItem(@RequestParam goodID: Int, @RequestHeader("auth") token: Token): ResponseEntity<String> {
        val userID = authService.getUserID(token) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        return if (cartService.setItem(userID, goodID, 0))
            ResponseEntity(HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping
    fun updateItem(@RequestParam goodID: Int, @RequestParam amount: Int,
                   @RequestHeader("auth") token: Token): ResponseEntity<String> {
        val userID = authService.getUserID(token) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        return if (cartService.setItem(userID, goodID, amount))
            ResponseEntity(HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.NOT_FOUND)
    }
}
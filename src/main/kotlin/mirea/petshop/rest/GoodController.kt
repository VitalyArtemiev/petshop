package mirea.petshop.rest

import mirea.petshop.model.Good
import mirea.petshop.model.GoodWrapper
import mirea.petshop.service.StoreService
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.concurrent.atomic.AtomicLong

@RestController
@RequestMapping("goods")
class GoodController @Autowired constructor(val storeService: StoreService) {
    @GetMapping("")
    fun getAll(): ResponseEntity<Array<GoodWrapper>> {
        return transaction{
            ResponseEntity(storeService.getAllGoods(), HttpStatus.OK)
        }
    }

    @GetMapping("/{goodID}")
    fun getByID(@PathVariable("goodID") goodID: Int): ResponseEntity<Good> {
        return transaction {
            val good = storeService.getGood(goodID)

            if (good == null) {
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
            else{
                ResponseEntity(good, HttpStatus.OK)
            }
        }
    }

    @PutMapping("/{goodID}")
    @PreAuthorize("@authService.hasRole({'Staff'}, #token)")
    fun addStock(@PathVariable("goodID") goodID: Int, @RequestParam amount: Int,
                 @RequestHeader("auth") token: Token): ResponseEntity<Good> {

        if (storeService.updateGood(goodID, amount)) {
            return ResponseEntity(HttpStatus.OK)
        } else
            return ResponseEntity(HttpStatus.NOT_FOUND)

    }
}

data class Greeting(val id: Long, val content: String)

@RestController
@RequestMapping("/")
class HelloController {
    val counter = AtomicLong()

    @GetMapping("/greeting", "")
    fun greeting(@RequestParam(value = "nameColumn", defaultValue = "World") name: String) =
            Greeting(counter.incrementAndGet(), "Hello, $name")
}
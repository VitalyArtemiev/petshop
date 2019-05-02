package mirea.petshop.rest

import mirea.petshop.model.Good
import mirea.petshop.model.GoodWrapper
import mirea.petshop.service.StoreService
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.atomic.AtomicLong

@RestController
@RequestMapping("goods")
class GoodController @Autowired constructor(val shopService: StoreService) {
    @GetMapping("")
    fun getAll(): ResponseEntity<Array<GoodWrapper>> {
        return transaction{
            ResponseEntity(shopService.getAllGoods(), HttpStatus.OK)
        }
    }

    @GetMapping("/{goodID}")
    fun getByID(@PathVariable("goodID") goodID: Int): ResponseEntity<Good> {
        return transaction {
            val good = shopService.getGood(goodID)

            if (good == null) {
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
            else{
                ResponseEntity(good, HttpStatus.OK)
            }
        }
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
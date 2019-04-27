package mirea.petshop.rest

import mirea.petshop.model.Pet
import mirea.petshop.model.PetWrapper
import mirea.petshop.service.StoreService
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.atomic.AtomicLong

@RestController
@RequestMapping("pets")
class PetController @Autowired constructor(val shopService: StoreService) {
    @RequestMapping("")
    fun getAll(): ResponseEntity<Array<PetWrapper>> {
        return transaction{
            ResponseEntity(shopService.getAllPets(), HttpStatus.OK)
        }
    }

    @RequestMapping("/{petID}")
    fun getByID(@PathVariable("petID") petID: Int): ResponseEntity<Pet> {
        return transaction {
            val pet = shopService.getPet(petID)

            if (pet == null) {
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
            else{
                ResponseEntity (pet, HttpStatus.OK)
            }
        }
    }
}

data class Greeting(val id: Long, val content: String)

@RestController
@RequestMapping("/")
class HelloController {
    val counter = AtomicLong()

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "nameColumn", defaultValue = "World") name: String) =
            Greeting(counter.incrementAndGet(), "Hello, $name")
}
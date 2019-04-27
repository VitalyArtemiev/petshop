package mirea.petshop.rest

import mirea.petshop.model.Item
import mirea.petshop.model.ItemWrapper
import mirea.petshop.service.StoreService
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("items")
class ItemController @Autowired constructor(val shopService: StoreService) {
    @RequestMapping("")
    fun getAll(): ResponseEntity<Array<ItemWrapper>> {
        return transaction{
            ResponseEntity(shopService.getAllItems(), HttpStatus.OK)
        }
    }

    @RequestMapping("/{itemID}")
    fun getByID(@PathVariable("itemID") ItemID: Int): ResponseEntity<Item> {
        return transaction {
            val item = shopService.getItem(ItemID)

            if (item == null) {
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
            else{
                ResponseEntity (item, HttpStatus.OK)
            }
        }
    }
}
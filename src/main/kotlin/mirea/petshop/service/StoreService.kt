package mirea.petshop.service

import mirea.petshop.model.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class StoreService {
    /*constructor() {
        Database.connect()

        transaction {

        }
    }*/

    val logger = LoggerFactory.getLogger("StoreLogger")


    val petDAO = PetDAO()
    val itemDAO = ItemDAO()

    fun getPet(ID: Int): Pet? {
        return petDAO.get(ID)
    }

    fun getAllPets(): Array<PetWrapper> {
        return petDAO.getAll().toTypedArray()
    }

    fun getItem(ID: Int): Item? {
        return itemDAO.get(ID)
    }

    fun getAllItems(): Array<ItemWrapper> {
        return itemDAO.getAll().toTypedArray()
    }
}
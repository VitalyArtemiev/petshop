package mirea.petshop.service

import mirea.petshop.model.Good
import mirea.petshop.model.GoodDAO
import mirea.petshop.model.GoodWrapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class StoreService @Autowired constructor(val goodDAO: GoodDAO) {

    val logger = LoggerFactory.getLogger("StoreLogger")

    fun getGood(ID: Int): Good? {
        return goodDAO.get(ID)
    }

    fun getAllGoods(): Array<GoodWrapper> {
        return goodDAO.getAll().toTypedArray()
    }
}
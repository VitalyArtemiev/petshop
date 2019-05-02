package mirea.petshop.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import mirea.petshop.DAO
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/*open class Good(id: Int, nameColumn: String, amt: Int, priceColumn: Double) {
    val ID = id
    val Name = nameColumn
    var Amount = amt
    val Price = priceColumn
}

class GoodWrapper(pet: Good) {
    val ID = pet.id
    val nameColumn = pet.nameColumn
    val priceColumn = pet.priceColumn
}*/

enum class GoodType { Pet, Item }

@Repository
@Transactional
class GoodDAO : DAO<Good, GoodWrapper> {
    /*@Autowired
    lateinit var db: Database*/

    override fun get(id: Int): Good? {
        return Good.findById(id)
    }

    override fun getAll(): List<GoodWrapper> {
        return GoodWrapper.all().toList()
    }

    override fun save(t: Good) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(t: Good, params: Array<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(t: Good) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

object Goods : IntIdTable("Goods") {
    //val id = integer("id").autoIncrement().primaryKey()
    val nameColumn = text("name")
    val descriptionColumn = text("description")
    val priceColumn = decimal("price", 10, 2)
    val amountColumn = integer("amount")
    val typeColumn = customEnumeration("type", "GoodType", { value -> GoodType.valueOf(value as String) }, { PGEnum("goodtype", it) })
}

@JsonIgnoreProperties("writeValues", "readValues", "_readValues", "db", "klass")
class Good(id: EntityID<Int>) : IntEntity(id) {
    companion object Table : IntEntityClass<Good>(Goods)

    val ID: Int by lazy {
        id.value
    }

    var name by Goods.nameColumn
    var description by Goods.descriptionColumn
    var price by Goods.priceColumn
    var amount by Goods.amountColumn
    var type by Goods.typeColumn
}

@JsonIgnoreProperties("writeValues", "readValues", "_readValues", "db", "klass")
class GoodWrapper(id: EntityID<Int>) : IntEntity(id) {
    companion object Table : IntEntityClass<GoodWrapper>(Goods)

    val ID: Int by lazy {
        id.value
    }

    var name by Goods.nameColumn
    var price by Goods.priceColumn
}
package mirea.petshop.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import mirea.petshop.DAO
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

//import


/*open class Item(id: Int, name: String, amt: Int, price: Double) {
    val ID = id
    val Name = name
    var Amount = amt
    val Price = price
}

class ItemWrapper(item: Item) {
    val ID = item.ID
    val name = item.Name
    val price = item.Price
}*/

class ItemDAO: DAO<Item, ItemWrapper> {
    override fun get(id: Int): Item? {
        return Item.findById(id)
    }

    override fun getAll(): List<ItemWrapper> {
        return ItemWrapper.all().toList()
    }

    override fun save(t: Item) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(t: Item, params: Array<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(t: Item) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

object Items: IntIdTable("miscgoods") {
    val nameColumn = text("name")
    val amountColumn = integer("amount")
    val priceColumn = decimal("price", 10, 2)
    val descriptionColumn = text("description")
}

@JsonIgnoreProperties("writeValues", "readValues", "_readValues", "db", "klass")
class Item(id: EntityID<Int>) : IntEntity(id) {
    companion object Table: IntEntityClass<Item>(Items)

    val ID: Int by lazy {
        id.value
    }

    var name by Items.nameColumn
    var amount by Items.amountColumn
    var price by Items.priceColumn
    var description by Items.descriptionColumn
}

@JsonIgnoreProperties("writeValues", "readValues", "_readValues", "db", "klass")
class ItemWrapper(id: EntityID<Int>) : IntEntity(id) {
    companion object Table: IntEntityClass<ItemWrapper>(Items)

    val ID: Int by lazy {
        id.value
    }

    var name by Items.nameColumn
    var price by Items.priceColumn
}

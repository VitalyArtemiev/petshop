package mirea.petshop.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import mirea.petshop.DAO
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/*open class Pet(id: Int, nameColumn: String, amt: Int, priceColumn: Double) {
    val ID = id
    val Name = nameColumn
    var Amount = amt
    val Price = priceColumn
}

class PetWrapper(pet: Pet) {
    val ID = pet.id
    val nameColumn = pet.nameColumn
    val priceColumn = pet.priceColumn
}*/

@Repository
@Transactional
class PetDAO: DAO <Pet, PetWrapper>  {
    /*@Autowired
    lateinit var db: Database*/

    override fun get(id: Int): Pet? {
        return Pet.findById(id)
    }

    override fun getAll(): List<PetWrapper> {
        return PetWrapper.all().toList()
    }

    override fun save(t: Pet) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(t: Pet, params: Array<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(t: Pet) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

object Pets: IntIdTable("Pets") {
    //val id = integer("id").autoIncrement().primaryKey()
    val nameColumn = text("name")
    val amountColumn = integer("amount")
    val priceColumn = decimal("price", 10, 2)
    val descriptionColumn = text("description")
}

@JsonIgnoreProperties("writeValues", "readValues", "_readValues", "db", "klass")
class Pet(id: EntityID<Int>) : IntEntity(id) {
    companion object Table: IntEntityClass<Pet>(Pets)

    val ID: Int by lazy {
        id.value
    }

    var name by Pets.nameColumn
    var amount by Pets.amountColumn
    var price by Pets.priceColumn
    var description by Pets.descriptionColumn
}

@JsonIgnoreProperties("writeValues", "readValues", "_readValues", "db", "klass")
class PetWrapper(id: EntityID<Int>) : IntEntity(id) {
    companion object Table: IntEntityClass<PetWrapper>(Pets)

    val ID: Int by lazy {
        id.value
    }

    var name by Pets.nameColumn
    var price by Pets.priceColumn
}
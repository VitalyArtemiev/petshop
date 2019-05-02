package mirea.petshop.model

open class Pet(id: Int, nameColumn: String, amt: Int, priceColumn: Double) {
    val ID = id
    val Name = nameColumn
    var Amount = amt
    val Price = priceColumn
}

class PetWrapper(pet: Pet) {
    val ID = pet.ID
    val nameColumn = pet.Name
    val priceColumn = pet.Price
}

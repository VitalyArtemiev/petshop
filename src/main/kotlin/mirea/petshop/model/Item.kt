package mirea.petshop.model


open class Item(id: Int, name: String, amt: Int, price: Double) {
    val ID = id
    val Name = name
    var Amount = amt
    val Price = price
}

class ItemWrapper(item: Item) {
    val ID = item.ID
    val name = item.Name
    val price = item.Price
}


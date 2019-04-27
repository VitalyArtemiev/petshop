package mirea.petshop

interface DAO<T, TW> {
    fun get(id: Int): T?

    fun getAll(): List<TW>

    fun save(t: T)

    fun update(t: T, params: Array<String>)

    fun delete(t: T)
}
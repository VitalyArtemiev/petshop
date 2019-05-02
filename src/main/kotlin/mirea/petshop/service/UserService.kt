package mirea.petshop.service

import mirea.petshop.model.User
import mirea.petshop.model.UserDAO
import mirea.petshop.model.UserWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserService @Autowired constructor(val userDao: UserDAO) {

    fun saveUser(u: User) {
        userDao.save(u)
    }

    fun getAll(): Array<UserWrapper> {
        return userDao.getAll().toTypedArray()
    }

    fun getUser(id: Int): User? {
        return userDao.get(id)
    }

    fun getUser(login: String): User? {
        return userDao.get(login)
    }
}
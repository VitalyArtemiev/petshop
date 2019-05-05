package mirea.petshop.rest

import mirea.petshop.model.User
import mirea.petshop.model.UserRole
import mirea.petshop.model.UserWrapper
import mirea.petshop.service.AuthService
import mirea.petshop.service.UserService
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

//import org.springframework.security.access.prepost.PreAuthorize
//import org.springframework.security.access.prepost.PreAuthorize

typealias Token = String


@RestController
@CrossOrigin(exposedHeaders = ["errors, content-type"])
@RequestMapping("users")
class UserController @Autowired constructor(val userService: UserService, val authService: AuthService) {
    @GetMapping("")
    @PreAuthorize("@authService.hasRole({'Admin'}, #token)")
    fun getAll(@RequestHeader("auth") token: Token): ResponseEntity<Array<UserWrapper>> {
        return transaction {
            ResponseEntity(userService.getAll(), HttpStatus.OK)
        }
    }

    @GetMapping("/{userID}")
    @PreAuthorize("@authService.hasRole({'Admin'}, #token)")
    fun getByID(@RequestParam id: Int, @RequestHeader("auth") token: Token): ResponseEntity<User> {
        return transaction {
            val user = userService.getUser(id)

            if (user == null) {
                ResponseEntity(HttpStatus.NOT_FOUND)
            } else {
                ResponseEntity(user, HttpStatus.OK)
            }
        }
    }

    @PostMapping("")
    fun login(@RequestParam login: String, @RequestParam password: String): ResponseEntity<Token> {
        return transaction {
            val user = userService.getUser(login)
            if (user == null)
                ResponseEntity<Token>(HttpStatus.NOT_FOUND)
            else {
                val token = authService.authUser(user, password)
                if (token == null)
                    ResponseEntity<Token>(HttpStatus.FORBIDDEN)
                else
                    ResponseEntity(token, HttpStatus.OK)
            }
        }
    }

    @PutMapping("")
    fun register(@RequestParam("login") login: String, @RequestParam("password") password: String,
                 @RequestParam("role", defaultValue = "User") role: UserRole,
                 @RequestHeader("auth", defaultValue = "") token: Token): ResponseEntity<Token> {
        if (role > UserRole.User) {
            if (!authService.hasRole(UserRole.Admin, token))
                return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }

        if (transaction { userService.getUser(login) != null })
            return ResponseEntity(HttpStatus.CONFLICT)
        else {
            var name = "Joe Shmuck"
            if (authService.registerUser(login, password, role, name))
                return ResponseEntity(HttpStatus.CREATED)
            else
                return ResponseEntity(HttpStatus.NOT_ACCEPTABLE)
        }
    }
}
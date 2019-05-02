package mirea.petshop.rest

import mirea.petshop.model.User
import mirea.petshop.model.UserRole
import mirea.petshop.model.UserWrapper
import mirea.petshop.service.AuthService
import mirea.petshop.service.UserService
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

//import org.springframework.security.access.prepost.PreAuthorize
//import org.springframework.security.access.prepost.PreAuthorize

typealias Token = String


@RestController
@CrossOrigin(exposedHeaders = ["errors, content-type"])
@RequestMapping("users")
class UserController @Autowired constructor(val userService: UserService, val authService: AuthService) {

    //@PreAuthorize("hasRole(@roles.ADMIN)")
    @PostMapping("", produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    @Throws(Exception::class)
    fun addOwner(@RequestBody @Valid user: User?, bindingResult: BindingResult): ResponseEntity<User> {
        //val errors = BindingErrorsResponse()
        val headers = HttpHeaders()
        if (bindingResult.hasErrors() || user == null) {
            //  errors.addAllErrors(bindingResult)
            //
            // headers.add("errors", errors.toJSON())
            return ResponseEntity<User>(user, headers, HttpStatus.BAD_REQUEST)
        }

        this.userService.saveUser(user)
        return ResponseEntity(user, headers, HttpStatus.CREATED)
    }

    @GetMapping("")
    @PreAuthorize("@authService.hasRole({'Admin'}, #token)")
    fun getAll(@RequestHeader("auth") token: Token): ResponseEntity<Array<UserWrapper>> {
        return transaction {
            ResponseEntity(userService.getAll(), HttpStatus.OK)
        }
    }

    @PostMapping("/login")
    fun login(@RequestParam login: String, @RequestParam password: String): ResponseEntity<Token> {
        return transaction {
            val user = userService.getUser(login)
            if (user == null)
                ResponseEntity<Token>(HttpStatus.NOT_FOUND)

            val token = authService.authUser(user!!, password)
            if (token == null)
                ResponseEntity<Token>(HttpStatus.FORBIDDEN)

            ResponseEntity(token!!, HttpStatus.OK)
        }
    }

    @PostMapping("/register")

    fun register(@RequestParam("login") login: String, @RequestParam("password") password: String): ResponseEntity<Token> {
        if (transaction { userService.getUser(login) != null })
            return ResponseEntity(HttpStatus.CONFLICT)
        else {
            transaction {
                val user = User.new {
                    this.login = login
                    salt = BCrypt.gensalt()
                    hash = BCrypt.hashpw(password, salt)
                    role = UserRole.User

                    name = "Joe Shmuck"
                }
            }

            return ResponseEntity(HttpStatus.CREATED)
        }
    }
}
package net.archasmiel.skufapi

import jakarta.persistence.EntityManager
import net.archasmiel.skufapi.api.enums.Role
import net.archasmiel.skufapi.api.exception.user.ResourceNotFoundException
import net.archasmiel.skufapi.api.exception.user.UserExistException
import net.archasmiel.skufapi.api.model.User
import net.archasmiel.skufapi.service.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class ApplicationTests {

	@Autowired
	lateinit var entityManager: EntityManager

	@Autowired
	lateinit var userService: UserService

	@Autowired
	lateinit var passwordEncoder: PasswordEncoder

	private fun createUser(
		username: String, email: String, password: String,
		role: Role, googleUser: Boolean
	): User = User(
		userName = username,
		email = email,
		passWord = password,
		role = role,
		googleUser = googleUser
	)

	private fun testUsers(): List<User> = listOf(
		createUser("user1", "user1@mail.com", "pwd1", Role.USER, false),
		createUser("user2", "user2@mail.com", "pwd2", Role.USER, false),
		createUser("user3", "user3@mail.com", "pwd3", Role.USER, false),
		createUser("user4", "user4@gmail.com", "pwd4", Role.USER, true),
		createUser("user5", "user5@gmail.com", "pwd5", Role.USER, true),
	)

	@BeforeEach
	fun clearDatabase() {
		entityManager.clear()
		entityManager.createQuery("DELETE FROM User").executeUpdate()
		entityManager.flush()
	}

	// ========== POSITIVE TESTS ==========

	@Test
	fun `get user by id should return correct user`() {
		val user = testUsers()[0].copy()
		val savedUser = userService.create(user)

		val foundUser = userService.getById(savedUser.id!!)
		assertEquals(savedUser.id, foundUser.id)
		assertEquals(user.userName, foundUser.userName)
	}

	@Test
	fun `update user should modify existing user`() {
		val original = testUsers()[0].copy()
		val saved = userService.create(original)

		val updatedData = saved.copy(
			userName = "updated_username",
			email = "updated@email.com"
		)
		val updatedUser = userService.update(updatedData)

		assertEquals("updated_username", updatedUser.userName)
		assertEquals("updated@email.com", updatedUser.email)
		assertEquals(saved.id, updatedUser.id)
	}

	@Test
	fun `delete user should remove from database`() {
		val user = testUsers()[0].copy()
		val saved = userService.create(user)

		userService.remove(saved.username)

		assertThrows<ResourceNotFoundException> {
			userService.getById(saved.id!!)
		}
	}

	@Test
	fun `get all users should return paginated results`() {
		val users = testUsers().map { it.copy() }
		users.forEach { userService.create(it) }

		val page1 = userService.getAll(PageRequest.of(0, 2))
		assertEquals(2, page1.size)

		val page2 = userService.getAll(PageRequest.of(1, 2))
		assertEquals(2, page2.size)

		assertNotEquals(page1, page2)   // may be irrelevant
	}

	// ========== NEGATIVE TESTS ==========

	@Test
	fun `create user with duplicate username should fail`() {
		val user1 = testUsers()[0].copy()
		userService.create(user1)

		val user2 = testUsers()[0].copy(email = "different@email.com")
		assertThrows<UserExistException> {
			userService.create(user2)
		}
	}

	@Test
	fun `create user with duplicate email should fail`() {
		val user1 = testUsers()[0].copy()
		userService.create(user1)

		val user2 = testUsers()[0].copy(userName = "different_username")
		assertThrows<UserExistException> {
			userService.create(user2)
		}
	}

	@Test
	fun `get non-existent user should throw exception`() {
		assertThrows<ResourceNotFoundException> {
			userService.getById(-1)
		}
	}

	@Test
	fun `update non-existent user should throw exception`() {
		val nonExistentUser = testUsers()[0].copy(id = -1)
		assertThrows<ResourceNotFoundException> {
			userService.update(nonExistentUser)
		}
	}

	// ========== EDGE CASE TESTS ==========

	@Test
	fun `create user with empty username should fail`() {
		val user = testUsers()[0].copy(userName = "")
		assertThrows<IllegalArgumentException> {
			userService.create(user)
		}
	}

	@Test
	fun `create user with invalid email should fail`() {
		val user = testUsers()[0].copy(email = "not-an-email")
		assertThrows<IllegalArgumentException> {
			userService.create(user)
		}
	}

	// ========== BUSINESS LOGIC TESTS ==========

	@Test
	fun `google users should have password set to empty string`() {
		val googleUser = testUsers()[3].copy() // Google user from test data
		val saved = userService.create(googleUser)

		assertEquals("", saved.password)
	}

	@Test
	fun `non-google users should preserve password`() {
		val regularUser = testUsers()[0].copy() // Non-google user
		val saved = userService.create(regularUser)

		assert(passwordEncoder.matches(regularUser.password, saved.password))
	}

	@Test
	fun `search users by username prefix should return matches`() {
		listOf(
			testUsers()[0].copy(userName = "alice_user"),
			testUsers()[1].copy(userName = "alice_admin"),
			testUsers()[2].copy(userName = "bob_user")
		).map { userService.create(it) }

		val results = userService.getAll("ali")
		assertEquals(2, results.size)
		assert(results.all { it.userName.startsWith("ali") })
	}

	// ========== SECURITY TESTS ==========

	@Test
	fun `passwords should be hashed before storage`() {
		val plainPassword = "my_plain_password"
		val user = testUsers()[0].copy(passWord = plainPassword)
		val saved = userService.create(user)

		assertNotEquals(plainPassword, saved.password)
		assert(saved.password.length > 20) // Basic check for hashing
	}

	@Test
	fun `admin users should have correct role`() {
		val adminUser = testUsers()[0].copy(role = Role.ADMIN)
		val saved = userService.create(adminUser)

		assertEquals(Role.ADMIN, saved.role)
	}
}
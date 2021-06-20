package com.alkemy.challenge.Blog;
import com.alkemy.challenge.Blog.models.User;
import com.alkemy.challenge.Blog.services.MyUserDetailsService;
import com.alkemy.challenge.Blog.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
class BlogApplicationTests {
	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private UserService userService;

	@Test //Test para crear un usuario.
	public void testCreateUser(){
		//Creamos un usuario
		User user = new User();
		//Le ponemos un Correo
		user.setEmail("junit@email.com");
		//Le ponemos una contraseña
		user.setPassword("genericPasswordHere");
		//Lo registramos
		myUserDetailsService.registerUser(user.getEmail(),user.getPassword());
		//Traemos el usuario que esta guardado en la DB
		User requestedUser = userService.findByEmail(user.getEmail());
		//Comparamos que los email sean iguales
		assertThat(user.getEmail())
				.isEqualTo(
						requestedUser.getEmail()
				);
		//Comparamos que las contraseñas sean iguales
		assertThat(user.getPassword())
				.isEqualTo(
						requestedUser.getPassword()
				);
		//Borramos el usuario que habiamos puesto en la DB.
		userService.deleteUser(requestedUser.getId());
	}

	@Test //Test donde deberíamos recibir un error al intentar acceder a un usuario ya registrado
	public void testExpectedExceptionTryingCreateAnRegisteredUser(){
		//Creamos un user que ya esta registrado.
		User user = new User();
		user.setEmail("testJUNIT@email.com");
		user.setPassword("genericPasswordHere");
		//Nos aseguramos que nos de un error de IllegalStateException.
		assertThrows(IllegalStateException.class,
				() -> {
			myUserDetailsService.registerUser(user.getEmail(),user.getPassword());
		});
	}

}

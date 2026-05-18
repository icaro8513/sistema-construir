package Construir.construir;

import Construir.construir.models.Cliente;
import Construir.construir.models.Persona;
import Construir.construir.models.TipoPersona;
import Construir.construir.repositories.PersonaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConstruirApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConstruirApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PersonaRepository personaRepository) {
		return (args) -> {
			if (personaRepository.findAll().isEmpty()) {
				Cliente cliente = new Cliente();
				cliente.setNombre("Juan");
				cliente.setApellido("Gómez");
				cliente.setEmail("juan@example.com");
				cliente.setContraseña("1234");
				cliente.setTelefono("123456789");
				cliente.setTipoPersona(TipoPersona.CLIENTE);
				cliente.setDireccion("Calle Falsa 123");

				personaRepository.save(cliente);

				System.out.println("Cliente guardado con éxito en la base de datos.");
			}
		};
	}
}

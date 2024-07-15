package com.alura.literalura;

import com.alura.literalura.main.Main;
import com.alura.literalura.repository.RepositorioAutor;
import com.alura.literalura.repository.RepositorioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private RepositorioLibro repositorioLibro;
	@Autowired
	private RepositorioAutor repositorioAutor;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(repositorioLibro, repositorioAutor);
		main.Menu();
	}
}

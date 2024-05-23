package com.aluracursos.Challenge_Literalura_BackEnd;

import com.aluracursos.Challenge_Literalura_BackEnd.main.Main;
import com.aluracursos.Challenge_Literalura_BackEnd.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiteraluraBackEndApplication implements CommandLineRunner {

	@Autowired
	private AutorRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiteraluraBackEndApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(repository);
		main.mostrarMenu();
	}
}

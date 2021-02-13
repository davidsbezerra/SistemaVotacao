package br.com.sicredi.votacao.testes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.sicredi.votacao.model.Associado;
import br.com.sicredi.votacao.repository.AssociadoRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TesteAssociado {

	@Autowired
	private AssociadoRepository associadoRepository;

	@Test
	public void salvarBuscarExcuirAssociado() {

		Associado associado = new Associado();
		associado.setNome("Nome");
		associado.setCpf("12345678909");

		associado = associadoRepository.save(associado);

		assertNotNull(associado.getId());

		if (associado.getId() != null) {

			assertNotNull(associadoRepository.findById(associado.getId()));

			associadoRepository.deleteById(associado.getId());

			assertFalse(associadoRepository.findById(associado.getId()).isPresent());
		}

	}

	@Test
	public void buscarAssociados() {

		Associado associado = new Associado();
		associado.setNome("Nome");
		associado.setCpf("12345678909");

		associado = associadoRepository.save(associado);

		assertTrue(!associadoRepository.findAll().isEmpty());

	}

}

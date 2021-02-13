package br.com.sicredi.votacao.testes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.sicredi.votacao.model.Associado;
import br.com.sicredi.votacao.repository.AssociadoRepository;
import br.com.sicredi.votacao.service.AssociadoService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TesteAssociado {

	@Autowired
	private AssociadoRepository associadoRepository;

	@Autowired
	private AssociadoService associadoService;

	@Test
	public void salvarBuscarExcuir() {

		Associado associado = new Associado();
		associado.setNome("Nome");
		associado.setCpf("12345678909");

		associado = associadoService.salvar(associado);

		assertNotNull(associado.getId());

		if (associado.getId() != null) {

			assertNotNull(associadoRepository.findById(associado.getId()));

			associadoService.excluir(associado.getId());

			assertFalse(associadoRepository.findById(associado.getId()).isPresent());
		}

	}

}

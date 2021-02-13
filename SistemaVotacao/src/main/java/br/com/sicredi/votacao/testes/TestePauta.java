package br.com.sicredi.votacao.testes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.sicredi.votacao.enums.StatusSessaoVotacaoEnum;
import br.com.sicredi.votacao.model.Pauta;
import br.com.sicredi.votacao.repository.PautaRepository;
import br.com.sicredi.votacao.service.PautaService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestePauta {

	@Autowired
	private PautaService pautaService;

	@Autowired
	private PautaRepository pautaRepository;

	@Test
	public void salvarExcuir() {

		Pauta pauta = new Pauta();
		pauta.setDescricao("Desrição da Pauta");
		pauta = pautaService.salvar(pauta);

		assertNotNull(pauta.getId());

		if (pauta.getId() != null) {

			pautaService.excluir(pauta.getId());

			assertFalse(pautaRepository.findById(pauta.getId()).isPresent());
		}

	}

	@Test
	public void abrirSessaoVotacao() {

		Pauta pauta = new Pauta();
		pauta.setDescricao("Desrição da Pauta");
		pauta = pautaService.salvar(pauta);

		pautaService.abrirSessaoVotacao(pauta);

		pauta = pautaRepository.findById(pauta.getId()).get();
		assertEquals(StatusSessaoVotacaoEnum.ABERTA, pauta.getSessaoVotacao().getStatusSessaoVotacao());

	}

}

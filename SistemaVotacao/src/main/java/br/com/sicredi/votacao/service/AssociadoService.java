package br.com.sicredi.votacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sicredi.votacao.model.Associado;
import br.com.sicredi.votacao.repository.AssociadoRepository;

@Service
public class AssociadoService {

	@Autowired
	private AssociadoRepository associadoRepository;

	public Associado salvar(Associado associado) {
		return associadoRepository.save(associado);
	}

	public void excluir(Long idAssociado) {
		associadoRepository.deleteById(idAssociado);
	}

}

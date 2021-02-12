package br.com.sicredi.votacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sicredi.votacao.model.VotacaoAssociado;

@Repository
public interface VotacaoAssociadoRepository extends JpaRepository<VotacaoAssociado, Long> {

}

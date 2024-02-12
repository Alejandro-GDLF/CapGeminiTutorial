package com.ccsw.tutorial.gameloan;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.ccsw.tutorial.gameloan.model.GameLoan;

public interface GameLoanRepository extends CrudRepository<GameLoan, Long>, JpaSpecificationExecutor<GameLoan> {

	@Override
	@EntityGraph(attributePaths= {"client", "game"})
    Page<GameLoan> findAll(Specification<GameLoan> spec, Pageable pageable);

	@Override
	@EntityGraph(attributePaths= {"client", "game"})
    long count(Specification<GameLoan> spec);
}

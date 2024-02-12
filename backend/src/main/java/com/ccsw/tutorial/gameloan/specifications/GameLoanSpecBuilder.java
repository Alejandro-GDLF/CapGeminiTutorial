package com.ccsw.tutorial.gameloan.specifications;

import org.springframework.data.jpa.domain.Specification;
import com.ccsw.tutorial.gameloan.model.*;
import com.ccsw.tutorial.common.criteria.*;
import com.ccsw.tutorial.gameloan.*;

public class GameLoanSpecBuilder {
	private Specification<GameLoan> spec;
	
	public GameLoanSpecBuilder(){
		this.spec = Specification.where(null);
	}
	
	public GameLoanSpecBuilder(SearchCriteria criteria) {
		this.spec = Specification.where(new GameLoanSpecification(criteria));
	}
	
	public GameLoanSpecBuilder(Specification<GameLoan> spec){
		this.spec = spec;
	}
	
	public GameLoanSpecBuilder and(SearchCriteria criteria) {
		this.spec = this.spec.and(new GameLoanSpecification(criteria));
		return this;
	}
	
	public GameLoanSpecBuilder or(SearchCriteria criteria) {
		this.spec = this.spec.or(new GameLoanSpecification(criteria));
		return this;
	}
	
	public GameLoanSpecBuilder and(Specification<GameLoan> specification) {
		this.spec = this.spec.and(specification);
		return this;
	}
	
	public GameLoanSpecBuilder or(Specification<GameLoan> specification) {
		this.spec = this.spec.or(specification);
		return this;
	}
	
	public Specification<GameLoan> build() {
		return this.spec;
	}
}

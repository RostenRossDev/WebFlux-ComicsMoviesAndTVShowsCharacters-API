package com.rostenross.webflux.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rostenross.webflux.model.Character;
import com.rostenross.webflux.repository.ICharacterRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ServiceCharacterImpl  implements ICharacterService{

	@Autowired
	private ICharacterRepository repo;
	
	@Override
	public Flux<Character> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	
	@Override
	public Flux<Character> getByName(String name) {
		// TODO Auto-generated method stub
		return repo.findByFullName(name);
	}

	@Override
	public Mono<Character> create(Character c) {
		// TODO Auto-generated method stub
		return repo.save(new Character(c.getFullName(), c.getDescription()));
	}

	@Override	
	public Mono<Character> update(Character character, String id) {
		// TODO Auto-generated method stub
		return repo.findById(id)
				.map(c-> new Character(c.getFullName(), c.getImgUri(), c.getDescription()))
				.flatMap(repo::save);
				//.switchIfEmpty(Mono.just(new Character()));
	}

	@Override
	public Mono<Character> delete(String id){
		// TODO Auto-generated method stub
		return repo.findById(id)
			.flatMap(c-> repo.delete(c).thenReturn(c));
	}

	@Override
	public Mono<Character> getById(String id) {
		// TODO Auto-generated method stub
		return repo.findById(id);//.switchIfEmpty(Mono.error(new NotFoundException()));
	}


	@Override
	public Mono<Void> deleteAll() {
		// TODO Auto-generated method stub
		Mono<Void> mono = repo.deleteAll().then();
		return mono;
	}

	

}

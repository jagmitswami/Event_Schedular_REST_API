package com.masai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masai.model.Customer;

public interface UserRepo extends JpaRepository<Customer, Integer>{

	public Optional<Customer> findByPhone(String phone);
}

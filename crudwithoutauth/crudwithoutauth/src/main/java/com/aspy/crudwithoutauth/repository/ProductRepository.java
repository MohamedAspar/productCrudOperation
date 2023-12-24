package com.aspy.crudwithoutauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aspy.crudwithoutauth.entity.product;
public interface ProductRepository extends JpaRepository<product,Integer> {



	

}

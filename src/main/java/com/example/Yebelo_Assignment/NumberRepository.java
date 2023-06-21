package com.example.Yebelo_Assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NumberRepository extends JpaRepository<NumberSequence, Integer> {

    Optional<NumberSequence> findByCategoryCode(String categoryCode);
}

package com.peeptodo.peeptodo_backend.repository;

import com.peeptodo.peeptodo_backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}

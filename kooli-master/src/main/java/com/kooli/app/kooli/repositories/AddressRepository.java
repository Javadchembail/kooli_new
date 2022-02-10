package com.kooli.app.kooli.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import com.kooli.app.kooli.models.Address;
import com.kooli.app.kooli.models.RecordStatus;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByStatus(RecordStatus recordStatus);

    List<Address> findByUserId(long id);

    Optional<Address> findByUserIdAndType(Long id, String type);

}
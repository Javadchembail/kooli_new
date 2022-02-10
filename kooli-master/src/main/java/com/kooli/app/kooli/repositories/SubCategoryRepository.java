package com.kooli.app.kooli.repositories;
import java.util.List;

import com.kooli.app.kooli.models.RecordStatus;
import com.kooli.app.kooli.models.SubCategory;

import org.springframework.data.jpa.repository.JpaRepository;



public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    List<SubCategory> findByStatus(RecordStatus recordStatus);

    List<SubCategory> findByCategoryId(long id);}


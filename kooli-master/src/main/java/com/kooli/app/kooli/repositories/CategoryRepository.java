package com.kooli.app.kooli.repositories;

import java.util.List;

import com.kooli.app.kooli.models.Category;
import com.kooli.app.kooli.models.RecordStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByStatus(RecordStatus recordStatus);

    @Query("SELECT DISTINCT p.category FROM Product p where p.user.id=:id AND p.status=:active")
    List<Category> getDistinctCategoryByUserIdAndStatus(@Param("id") long id, @Param("active") RecordStatus active);

}
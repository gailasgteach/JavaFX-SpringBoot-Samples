// MusicRepository.java - Music repository
package com.asgteach.springbootrest.service;
import com.asgteach.springbootrest.domain.MusicCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MusicRepository 
        extends JpaRepository<MusicCategory, Integer> {
    @Query(
        nativeQuery = true,
            value = "select  * from musiccategories " +
                " where musiccategory = :category " +
                    " limit 1"
    )
    MusicCategory findByCategoryName(@Param("category") String category); 
}

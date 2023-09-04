package com.lanuza.phildrinkswms.data.repository;

import com.lanuza.phildrinkswms.data.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query("select p from Supplier p " +
            "where lower(p.supplier) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(p.address) like lower(concat('%', :searchTerm, '%'))")
    List<Supplier> search(@Param("searchTerm") String searchTerm);
}

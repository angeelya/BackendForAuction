package dip.server.repository;

import dip.server.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {
    @Query(value = "select b.name from brand b where id_brand=:id_brand",nativeQuery = true)
    String findBrandName(@Param("id_brand") long id_brand);
    @Query(value = "select * from brand b order by name",nativeQuery = true)
    List<Brand> findBrand();
    @Query(value = "select b.name from brand b",nativeQuery = true)
    List<String> findAllBrandName();
}

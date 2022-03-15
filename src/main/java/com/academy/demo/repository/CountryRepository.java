package com.academy.demo.repository;

import com.academy.demo.dto.CountryDTO;
import com.academy.demo.entity.Country;
import com.academy.demo.projection.CountryIdAndShortCodeProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer>
{
    @Query(value = "select country from Country as country")
    List<Country> findAllCountries();
    // SELECT id, name, short_code
    // FROM country

    // Pageable (page, size, Sort) -> column,asc|desc
    // page -> offset
    // size -> limit
    // order by column asc|desc

    @Query(value = "select new com.academy.demo.dto.CountryDTO(country.id, country.shortCode) " +
            "from Country as country")
    List<CountryDTO> findIdAndShortCode();

    @Query(value = "select new com.academy.demo.dto.CountryDTO(country.name, country.shortCode) " +
            "from Country as country")
    List<CountryDTO> findNameAndShortCode();

    @Query(value = "select country.id as id, country.shortCode as shortCode from Country as country")
    List<Tuple> findIdAndShortCodeUsingTuple();

    @Query(value = "select country.id as id, country.shortCode as shortCode from Country as country ")
    List<CountryIdAndShortCodeProjection> findIdAndShortCodeUsingCustomProjection();

//    @Query(value = "select country.id as id, country.shortCode as shortCode from Country as country " +
//            "where country.id = 1")
//    CountryIdAndShortCodeProjection findIdAndShortCodeUsingCustomProjection();

    // TODO: do not use!!!
    @Query(value = "select * from country", nativeQuery = true)
    List<Country> findAllUsingNativeQuery();

//    @Query(value = "select id, short_code as shortCode from country " +
//            "where country.name = :name", nativeQuery = true)
//    List<CountryIdAndShortCodeProjection> findIdAndShortCodeByNameUsingNativeQuery(@Param("name") String name);

    @Query(value = "select id, short_code as shortCode from country", nativeQuery = true)
    List<CountryIdAndShortCodeProjection> findIdAndShortCodeUsingNativeQuery();


    // Page<Country> findAll(Pageable pageable);
    // SELECT id, name, short_code
    // FROM country
    // LIMIT 100
    // total elements -> SELECT COUNT(*) FROM country
    // total pages -> depends on size (provided limit)

    @Query(
        value = "select country from Country country join fetch country.cities",
        countQuery = "select count(country) from Country country"
    )
    Page<Country> findAllUsingJPQL(Pageable pageable);

    @Query(value = "select country.id from Country country")
    Page<Integer> findIdsPageable(Pageable pageable);

    @Query(value = "select distinct country from Country country " +
            "join fetch country.cities " +
            "where country.id in (:countryIds)")
    List<Country> findByIdIn(@Param("countryIds") List<Integer> countryIds);

    @Query(
        value = "select id, short_code as shortCode from country",
        countQuery = "select count(*) from country",
        nativeQuery = true)
    Page<CountryIdAndShortCodeProjection> findPageableUsingNativeQuery(Pageable pageable);
}

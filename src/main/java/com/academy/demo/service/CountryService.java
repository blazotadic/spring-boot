package com.academy.demo.service;

import com.academy.demo.dto.CountryDTO;
import com.academy.demo.entity.Country;
import com.academy.demo.mapper.CountryMapper;
import com.academy.demo.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;


    public Page<Country> findPage(Pageable pageable) {
        return countryRepository.findAllUsingJPQL(pageable);
    }

    public List<Country> findAllPageable(Pageable pageable)
    {
        Page<Integer> countryIdsPage = countryRepository.findIdsPageable(pageable); // [1, 2, 3]
        List<Integer> countryIds = countryIdsPage.getContent();

        return countryRepository.findByIdIn(countryIds);
    }

    @Transactional(propagation = Propagation.NEVER)
    public void save(CountryDTO countryDTO)
    {
        countryDTO.setId(null);

        countryRepository.save(
            countryMapper.convertToEntity(countryDTO)
        );

        throw new IllegalArgumentException("Country error!");
    }

    @Transactional
    public void update(Integer id, CountryDTO countryDTO)
    {
        Optional<Country> countryOptional = countryRepository.findById(id);
        if (countryOptional.isPresent())
        {
            Country country = countryOptional.get();
            country.setName(countryDTO.getName());
            country.setShortCode(countryDTO.getCode());

            countryRepository.save(country);
        }
        else {
            throw new IllegalArgumentException("Country not exists!");
        }
    }

    public void delete(Integer id)
    {
        countryRepository.deleteById(id);
        // select ... where id = ?
        // delete from country where id = ?
    }

    public void deleteWithEntity(Integer id)
    {
        Optional<Country> countryOptional = countryRepository.findById(id);
        if (countryOptional.isPresent())
        {
            Country country = countryOptional.get();
            countryRepository.delete(country);
        }
    }
}

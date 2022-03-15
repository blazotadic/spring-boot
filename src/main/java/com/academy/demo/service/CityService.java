package com.academy.demo.service;

import com.academy.demo.dto.CityDTO;
import com.academy.demo.entity.City;
import com.academy.demo.mapper.CityMapper;
import com.academy.demo.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;


    @Cacheable(value = "city", key = "#id", condition = "#id > 5")
    public CityDTO findById(Integer id)
    {
        City city = cityRepository.findById(id).orElse(null);
        return cityMapper.toDTO(city);


        // SELECT * FROM city WHERE id = ?"
        //
        //
        //
    }

    @Cacheable(value = "cities", key = "'all'")
    public List<CityDTO> findAll()
    {
        List<City> cities = cityRepository.findAllWithCountries();
        return cities.stream().map(cityMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    @CachePut(cacheNames = "city", key = "#result.id")
    public CityDTO save(CityDTO cityDTO)
    {
        log.info("Saving new city...");

        City city = cityMapper.mapToEntity(cityDTO);
        return cityMapper.toDTO(city);
    }

    @Transactional
    @CacheEvict(cacheNames = "city", key = "#id")
    public void delete(Integer id) {
        cityRepository.deleteById(id);
    }

    @CacheEvict(cacheNames = "city", allEntries = true)
    public void evictAllCache() {
        log.info("Removing all cache entries...");
    }
}

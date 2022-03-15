package com.academy.demo.service;

import com.academy.demo.dto.fakestore.CartExtendedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CartService {

    @Autowired
    @Qualifier("secureRestTemplate")
    private RestTemplate restTemplate;


    public CartExtendedDTO findFakeById(Integer id)
    {
        String url = "https://fakestoreapi.com/carts/" + id;
        try
        {
            ResponseEntity<CartExtendedDTO> responseEntity = restTemplate.exchange(
                url, HttpMethod.GET, null, CartExtendedDTO.class
            );

            return responseEntity.getBody();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}

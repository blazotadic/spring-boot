package com.academy.demo.controller;

import com.academy.demo.dto.fakestore.CartExtendedDTO;
import com.academy.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    /**
     * Getting extended data about cart | GET: /api/cart/{id}
     *
     * @param id unique identifier of cart
     * @return ResponseEntity with CartExtendedDTO Object and Http Status Code (200 | 204)
     */
    @GetMapping(value = "{id}")
    public ResponseEntity<CartExtendedDTO> oneById(@PathVariable Integer id)
    {
        CartExtendedDTO cart = cartService.findFakeById(id);
        cart.setStatus("OK");
        cart.setMessage("Testna poruka");

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}

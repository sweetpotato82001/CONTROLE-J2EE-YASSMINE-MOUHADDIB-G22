package org.sid.inventoryservice.controller;

import com.netflix.discovery.converters.Auto;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.sid.inventoryservice.enteties.Product;
import org.sid.inventoryservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class InventoryController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private KeycloakRestTemplate keycloakRestTemplate;
    
    @PostMapping("/add_product")
    public Product addProduct(@RequestBody Product product){
        return productRepository.save(product);
    }

    @GetMapping("/pdocuts")
    public String products(Model model){
        PagedModel<Product> pageProducts = keycloakRestTemplate.getForObject("http://localhost:8082/products",PagedModel.class);
        model.addAttribute("products",pageProducts);
        return  "products";
    }


    @DeleteMapping("/delete_product/{Id}")
    public void deleteProduct(@PathVariable("Id") Long id) {
        this.productRepository.deleteById(id);
    }

    @GetMapping("/jwt")
    @ResponseBody
     public Map<String,String> map(HttpServletRequest request){
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
        KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        KeycloakSecurityContext keycloakSecurityContext = principal.getKeycloakSecurityContext();
        Map<String,String> map= new HashMap<>();
        map.put("access_token",keycloakSecurityContext.getIdTokenString());
        return map;
    }

}

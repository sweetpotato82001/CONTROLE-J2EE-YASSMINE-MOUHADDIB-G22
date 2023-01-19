package org.sid.billingservice;

import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.entities.ProductItem;
import org.sid.billingservice.feign.ProductItemRestClient;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.repository.BillRepository;
import org.sid.billingservice.repository.ProductItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
public class BillController {

    @Autowired
    BillRepository billRepository;

    @Autowired
    ProductItemRepository productItemRepository;

    @Autowired
    ProductItemRestClient productItemRestClient;

    //ADD BILL
    @PostMapping("/add_bill")
    public void addBill(@RequestBody Bill bill){

        Bill bill1 = billRepository.save(new Bill(null, bill.getBillingDate(),null,bill.getCustomerID(),null));
        Collection<ProductItem> productItems = bill.getProductItems();
        productItems.forEach(
                p ->{
                    p.setBill(bill1);
                    productItemRepository.save(p);
                }
        );

    }


    //DELETE BILL
    @DeleteMapping("/delete_bill/{Id}")
    public void deleteBill(@PathVariable("Id") Long id) {
        Bill bill = this.billRepository.findById(id).get();
        Collection<ProductItem> productItems = bill.getProductItems();
        productItemRepository.deleteAll(productItems);
        billRepository.deleteById(id);
    }
}

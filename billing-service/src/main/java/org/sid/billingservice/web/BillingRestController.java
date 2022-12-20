package org.sid.billingservice.web;

import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.entities.ProductItem;
import org.sid.billingservice.feign.CustomerRestClient;
import org.sid.billingservice.feign.ProductItemRestClient;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.model.customer;
import org.sid.billingservice.repository.BillRepository;
import org.sid.billingservice.repository.ProductItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class BillingRestController {
    private BillRepository billRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private ProductItemRestClient productItemRestClient;

    public BillingRestController(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductItemRestClient productItemRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productItemRestClient = productItemRestClient;
    }

    @GetMapping(path = "/fullbill/{id}")
    public Bill getBill(@PathVariable(name="id") Long id){
        Bill bill=billRepository.findById(id).get();
        customer customer=customerRestClient.getCustomerById(bill.getCustomerID());
        bill.setCustomer(customer);
        bill.getProductItems().forEach(
                pi->{
                    Product product= productItemRestClient.getProductById(pi.getProductID());
                    //pi.setProduct(product);
                    pi.setProductName(product.getName());
                }
        );
        return bill;
    }
    @GetMapping(path = "/bills1")
    public List<Bill> getBills1(){
        return billRepository.findAll();
    }
    @GetMapping(path = "/bills")
    public List<Bill> getBills(){
        long size=billRepository.count();
        List<Bill> bills = new ArrayList<Bill>();
        Bill bill;
        customer customer;
        for(long i=1;i<=size;i++)
        {
             bill=billRepository.findById(i).get();
             customer=customerRestClient.getCustomerById(bill.getCustomerID());
            bill.setCustomer(customer);
            bill.getProductItems().forEach(
                    pi->{
                       Product  product= productItemRestClient.getProductById(pi.getProductID());

                        pi.setProductName(product.getName());
                    });
            bills.add(bill);
        }

      return bills;
    }
    @GetMapping(path = "/billsProducts/{id}")

        public Collection<ProductItem> getBillproduct(@PathVariable(name="id") Long id){
            Bill bill=billRepository.findById(id).get();
            customer customer=customerRestClient.getCustomerById(bill.getCustomerID());
            bill.setCustomer(customer);
            bill.getProductItems().forEach(
                    pi->{
                        Product product= productItemRestClient.getProductById(pi.getProductID());
                        //pi.setProduct(product);
                        pi.setProductName(product.getName());
                    }
            );
            return bill.getProductItems();
        }


}

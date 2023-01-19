package org.sid.billingservice;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.entities.ProductItem;
import org.sid.billingservice.feign.ProductItemRestClient;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.repository.BillRepository;
import org.sid.billingservice.repository.ProductItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

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



    @GetMapping(value = "/analyticsWindows",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Map<String,Long>> analyticsWindows(){
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq->{
                    Map<String,Long> map=new HashMap<>();
                    InteractiveQueryService interactiveQueryService = null;
                    ReadOnlyWindowStore<String, Long> stats = interactiveQueryService.getQueryableStore("count-store", QueryableStoreTypes.windowStore());
                    Instant now=Instant.now();
                    Instant from=now.minusSeconds(30);
                    KeyValueIterator<Windowed<String>, Long> windowedLongKeyValueIterator = stats.fetchAll(from, now);
                    while (windowedLongKeyValueIterator.hasNext()){
                        KeyValue<Windowed<String>, Long> next = windowedLongKeyValueIterator.next();
                        map.put(next.key.key(),next.value);
                    }
                    return map;
                });
    }
}

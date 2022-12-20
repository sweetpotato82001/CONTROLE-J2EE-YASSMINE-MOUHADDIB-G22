import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";


@Component({
  selector: 'app-billing',
  templateUrl: './billing.component.html',
  styleUrls: ['./billing.component.css']
})
export class BillingComponent implements OnInit {

  //billing: any;

  billing = [
    { id:1,
      billingDate:"",
      productItems:[
        {
          id:1,
          quantity:0,
          price:0,
          productID:0,
          product:null,
          productName:""
        }
      ]
    }
  ]
  billing1 : any


  constructor(private http: HttpClient,private router:Router) {
  }

  ngOnInit(): void {
    this.http.get("http://localhost:8888/BILLING-SERVICE/bills?projection=fullbill")

      .subscribe({
        next: (data) => {
          this.billing1=data;
          this.billing = this.billing1


        },
        error: (err) => {
        }
      });

  }
  getProducts(p:any){
    let id=p.id;
    this.router.navigate(['/products/',id]);
  }
}

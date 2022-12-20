import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {
   products:any
  billinId: any
  constructor(private http: HttpClient,private router:Router,private route:ActivatedRoute) {




  }

  ngOnInit(): void {
    let id = parseInt(this.route.snapshot.paramMap.get('id') || '{}');
    this.billinId = id;
    this.http.get("http://localhost:8888/BILLING-SERVICE/billsProducts/"+id)

      .subscribe({
        next: (data) => {
          this.products=data;




        },
        error: (err) => {
        }
      });

  }


}

import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  public product={
      name: '',
      price: 0,
      quantity: 0
  }
  constructor(private _hhtp:HttpClient,private _router:Router) { }

  ngOnInit(): void {
  }
  formSubmit(){
    this._hhtp.post("http://localhost:8888/PRODUCT-SERVICE/add_product",this.product).subscribe(
      (data:any)=>{
          this._router.navigate(['/products'])
      }
    )
}
}

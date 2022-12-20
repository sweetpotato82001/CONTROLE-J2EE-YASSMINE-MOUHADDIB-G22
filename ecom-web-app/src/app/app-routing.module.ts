import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ProductsComponent} from "./products/products.component";
import {CustomersComponent} from "./customers/customers.component";
import {BillingComponent} from "./billing/billing.component";
import {OrdersComponent} from "./orders/orders.component";
import { AddCustomerComponent } from './add-customer/add-customer.component';
import { AddProductComponent } from './add-product/add-product.component';

const routes: Routes = [
  {
    path:"products",component:ProductsComponent
  },
  {
    path:"customers",component:CustomersComponent
  },
  {
    path:"billing",component:BillingComponent
  },
  {
    path: 'products/:id', component: OrdersComponent
  },
  {
    path: 'add-customer', component: AddCustomerComponent
  },
  {
    path: 'add-product', component:AddProductComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

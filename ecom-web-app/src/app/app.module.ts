import {APP_INITIALIZER, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductsComponent } from './products/products.component';
import {HttpClientModule} from "@angular/common/http";
import { CustomersComponent } from './customers/customers.component';
import { OrdersComponent } from './orders/orders.component';
import { BillingComponent } from './billing/billing.component';
import {MatMenuModule} from '@angular/material/menu';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import {KeycloakAngularModule,KeycloakService} from 'keycloak-angular';
import { AddCustomerComponent } from './add-customer/add-customer.component'
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatCardModule} from '@angular/material/card';
import {MatInputModule} from '@angular/material'
import { FormsModule } from '@angular/forms';
import { AddProductComponent } from './add-product/add-product.component';

function initializeKeycloak(keycloak: KeycloakService){
  return() =>{
      keycloak.init({
        config:{
          url: 'http://localhost:8080/auth',
          realm: 'my-ecom-realm',
          clientId: 'ecom-app'
        },
        initOptions: {
          onLoad: 'login-required',
        }
      })
  }
}


@NgModule({
  declarations: [
    AppComponent,
    ProductsComponent,
    CustomersComponent,
    OrdersComponent,
    BillingComponent,
    AddCustomerComponent,
    AddProductComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatMenuModule,
    BrowserAnimationsModule,
    RouterModule,
    KeycloakAngularModule,
    MatFormFieldModule,
    MatCardModule,
    MatInputModule,
    FormsModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps : [KeycloakService],
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

import {Component, OnInit, ViewChild} from '@angular/core';
import {ServiceService} from "../service/service.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-load-payments',
  templateUrl: './load-payments.component.html',
  styleUrl: './load-payments.component.css'
})
export class LoadPaymentsComponent implements OnInit{
  payments:any;
  public dataSource:any;
  public displayedColumns=['id','date','amount',
    'paymentType','paymentStatus','firstName'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(private service:ServiceService) {
  }
  ngOnInit(): void {
    this.loadPayments();
  }

  loadPayments(){
    this.service.loadPayment()
      .subscribe({
        next:resp=>{
          this.payments = resp;
          //console.log(" resp "+JSON.stringify(resp))
           this.dataSource=new MatTableDataSource(this.payments)
          this.dataSource.paginator=this.paginator
        },error:err=>{
          console.log(err);
        }
      })
  }
}

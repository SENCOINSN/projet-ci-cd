import { Component } from '@angular/core';
import {ServiceService} from "../service/service.service";

@Component({
  selector: 'app-admin-template',
  templateUrl: './admin-template.component.html',
  styleUrl: './admin-template.component.css'
})
export class AdminTemplateComponent {

  constructor(public service:ServiceService) {
  }

  logout(){
   this.service.logout()
  }

}

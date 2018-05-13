import { Component, OnInit } from '@angular/core';
import {HttpService} from '../../services/httpService/http.service';

@Component({
  selector: 'app-page1',
  templateUrl: './check-operations.component.html',
  styleUrls: ['./check-operations.component.css'],
  providers: [HttpService]
})

export class CheckOperationsComponent implements OnInit {

  constructor() { }

  ngOnInit() {

  }
}

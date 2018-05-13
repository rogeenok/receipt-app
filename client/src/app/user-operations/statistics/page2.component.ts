import { Component, OnInit } from '@angular/core';
import { StatsService } from "../../services/statsService/stats.service";
import {MessageProcessingService} from "../../services/messageService/message.processing.service";
import {Stats} from "../statsData/stats";

@Component({
  selector: 'app-page2',
  providers: [StatsService],
  templateUrl: './page2.component.html',
  styleUrls: ['./page2.component.css']
})
export class Page2Component implements OnInit {
  stats : Stats;

  constructor(private statsService: StatsService,
              private proc: MessageProcessingService) { }

  ngOnInit() {
    this.getChecksInfo();
  }

  getChecksInfo() {
    this.statsService.getChecks(null)
      .subscribe((data) => {
        this.stats = data;
      },
      error => {
        this.proc.showMessage("Ошибка при получении статистики");
      });
  }

}



import {Component, OnInit} from '@angular/core';
import {ReportService} from "../../services/report.service";
import {Report} from "../../model/report";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {
  report!: Report
  patId!: number;
  errorMessage!: string;
  constructor(
    private reportService: ReportService,
    private router: Router,
    private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.patId = this.route.snapshot.params['patId'];
    this.generateReportByPatId(this.patId);
  }


  private generateReportByPatId(patId: number) {
    return this.reportService.generateReportByPatId(patId).subscribe({
      next: generatedReport => {
        this.report = generatedReport;
        console.log(generatedReport);
      },
      error: err => {
        console.log(err);
        this.errorMessage = err.error.message;
      }
    })
  }
}

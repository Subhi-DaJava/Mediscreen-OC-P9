import {Component, OnInit} from '@angular/core';
import {Report} from "../../model/report";
import {ReportService} from "../../services/report.service";
import {ActivatedRoute, Router} from "@angular/router";
import {PatientService} from "../../services/patient.service";

@Component({
  selector: 'app-report-by-name',
  templateUrl: './report-by-name.component.html',
  styleUrls: ['./report-by-name.component.css']
})
export class ReportByNameComponent implements OnInit {
  report!: Report
  patName!: string;
  patId!: number;
  errorMessage!: string;

  constructor(
    private reportService: ReportService,
    private router: Router,
    private route: ActivatedRoute,
    private patientService: PatientService) {
  }

  ngOnInit(): void {
    this.patName = this.route.snapshot.params['patName'];
    this.generateReportByPatName(this.patName);
    this.getPatientByPatLastName(this.patName);
  }

  private generateReportByPatName(patName: string) {
    return this.reportService.generateReportByPatName(patName).subscribe({
      next: generatedReport => {
        this.report = generatedReport;
        console.log(generatedReport);
      },
      error: err => {
        console.log(err);
        this.errorMessage = err.error.message;
      }
    });
  }

  private getPatientByPatLastName(lastName: string) {
    this.patientService.getPatientByLastName(lastName).subscribe({
      next: patientGetByPatLastName => {
        this.patId = patientGetByPatLastName.id;
        console.log(patientGetByPatLastName);
      }, error: err => {
        this.errorMessage = err.error.message;
      }
    });
  }
}


import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Patient} from 'src/app/model/patient';
import {PatientService} from 'src/app/services/patient.service';

@Component({
  selector: 'app-create-patient',
  templateUrl: './create-patient.component.html',
  styleUrls: ['./create-patient.component.css']
})
export class CreatePatientComponent implements OnInit {

  patient: Patient = new Patient();
  errorMessage!: string;
  errorMessages!: string[];

  constructor(
    private patientService: PatientService,
    private router: Router) {
  }

  ngOnInit(): void {
  }

  addPatient() {
    this.patientService.createPatient(this.patient).subscribe({
      next: data => {
        console.log(data);
        this.goToPatientList();
      },
      error: error => {
        console.log(error);
        this.errorMessage = error.error.message;
        this.errorMessages = error.error.errors ? error.error.errors : 'An error occurred while validating the form';
      }
    });
  }

  goToPatientList() {
    this.router.navigate(['/patients']).then()
  }

  onSubmit() {
    console.log(this.patient);
    this.addPatient();
  }
}



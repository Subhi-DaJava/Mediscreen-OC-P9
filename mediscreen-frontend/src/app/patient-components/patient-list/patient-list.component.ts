import {Component, OnInit} from '@angular/core';
import {Patient} from '../../model/patient';
import {PatientService} from '../../services/patient.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.css']
})
export class PatientListComponent implements OnInit {
  searchLastName: string = '';
  patients!: Patient[];
  errorMessage!: string;

  constructor(
    private patientService: PatientService,
    private router: Router) {
  }

  ngOnInit(): void {
    this.getPatientList();
  }

  private getPatientList() {
    this.patientService.getPatientList().subscribe({
      next: data => {
        this.patients = data;
      },
      error: err => {
        console.log(err);
      }
    });
  }

  updatePatient(id: number) {
    this.router.navigate(['update-patient', id]).then();
  }

  deletePatient(id: number) {
    const confirmDelete = confirm('Are you sure you want to delete this Patient?');
    if (!confirmDelete) {
      return;
    }
    this.patientService.deleteById(id).subscribe({
      next: data => {
        console.log(data);
        this.getPatientList();
      },
      error: error => {
        console.log(error);
        this.errorMessage = error.error.message;
      }
    });

  }

  showPatientDetails(id: number) {
    this.router.navigate(['patient-details', id]).then();
  }

  searchPatientByLastName() {
    if (!this.searchLastName) {
      this.errorMessage = "Last Name field is empty!";
      return;
    }
    this.patientService.getPatientByLastName(this.searchLastName).subscribe({
      next: patient => {
        this.router.navigate(['/patient-details', patient.id]).then()
      },
      error: error => {
        console.log('Error while searching for patient by last name:', error);
        this.errorMessage = error.error.message;
      }
    });
  }
}

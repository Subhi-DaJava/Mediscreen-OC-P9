import {Component, OnInit} from '@angular/core';
import {NoteService} from "../../services/note.service";
import {Note} from "../../model/note";
import {ActivatedRoute, Router} from "@angular/router";
import {PatientService} from "../../services/patient.service";
import {Patient} from "../../model/patient";

@Component({
  selector: 'app-create-note-by-pat-id',
  templateUrl: './create-note-by-pat-id.component.html',
  styleUrls: ['./create-note-by-pat-id.component.css']
})
export class CreateNoteByPatIdComponent  implements OnInit{
  note: Note = new Note();
  patId!: number;
  patient!: Patient;
  errorMessage!: string;

  constructor(
    private noteService: NoteService,
    private route: ActivatedRoute,
    private router: Router,
    private patientService: PatientService) {
  }
  ngOnInit(): void {
    this.patId = this.route.snapshot.params['patId'];
    this.getPatientById(this.patId);
  }

  ngSubmit() {
    this.note.patId = this.patId;
    this.note.patLastName = this.patient.lastName;

    this.noteService.createNewNote(this.note).subscribe({
      next: newNote => {
        console.log(newNote);
        this.router.navigate(['/patient-details', this.patId]).then();
      },
      error: err => {
        console.log(err);
        this.errorMessage = err.error.message;
      }
    });

  }

  getPatientById(id: number) {
    return this.patientService.getPatientById(id).subscribe({
      next: data => {
        console.log(data)
        this.patient = data;
      },
      error: error => {
        console.log(error)
        this.errorMessage = error.error.message;
      }
    });
  }
}

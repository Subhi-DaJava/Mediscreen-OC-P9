import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Note} from 'src/app/model/note';
import {NoteService} from 'src/app/services/note.service';
import {PatientService} from 'src/app/services/patient.service';
import {Patient} from "../../model/patient";

@Component({
  selector: 'app-add-note-to-patient',
  templateUrl: './add-note-to-patient.component.html',
  styleUrls: ['./add-note-to-patient.component.css']
})
export class AddNoteToPatientComponent implements OnInit {
  patid!: number;
  notes!: Note[];
  errorMessage!: string;
  comment!: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private noteService: NoteService) {
  }

  ngOnInit(): void {
    this.patid = this.route.snapshot.params['patid'];
    this.getNotesByPatId(this.patid);
  }

  addNewCommentToPatient(patientId: number, doctorComment: string) {

    this.noteService.addNoteToPatientByPatId(patientId, doctorComment).subscribe({
      next: noteByPatId => {
        console.log(noteByPatId);
        this.goToPatientDetails();
      },
      error: err => {
        console.log(err);
        this.errorMessage = err.error.message;
      }
    });

  }

  getNotesByPatId(patientId: number) {
    this.noteService.getNotesByPatId(patientId).subscribe({
      next: allNotes => {
        this.notes = allNotes;
        console.log(allNotes);
      },
      error: err => {
        console.log(err.error);
        console.log(err.Error);
        this.errorMessage = err.error;
      }
    })
  }

  goToPatientDetails() {
    this.router.navigate(['/patient-details', this.patid]).then();
  }

  ngSubmit() {
    if (!this.comment) {
      this.errorMessage = "Should not be empty";
      return;
    }
    this.addNewCommentToPatient(this.patid, this.comment);
  }
}

import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Note} from 'src/app/model/note';
import {NoteService} from 'src/app/services/note.service';

@Component({
  selector: 'app-note-list',
  templateUrl: './note-list.component.html',
  styleUrls: ['./note-list.component.css']
})
export class NoteListComponent implements OnInit {
  notes!: Note[];
  searchByLastName: string = '';
  searchByPatientId!: number;
  errorMessagePatId!: string;
  errorMessageLastName!: string;

  constructor(
    private noteService: NoteService,
    private router: Router) {
  }

  ngOnInit(): void {
    this.getAllNotes();
  }

  private getAllNotes() {
    this.noteService.getAllNotes().subscribe({
      next: allNotes => {
        this.notes = allNotes;
      },
      error: err => console.log(err)
    });
  }

  updateNoteById(id: string) {
    this.router.navigate(['/update-note', id]).then();
  }

  deleteNoteById(id: string) {
    const confirmDelete = confirm('Are you sure you want to delete this Note?');
    if (!confirmDelete) {
      return;
    }
    this.noteService.deleteNoteById(id).subscribe({
      next: data => {
        console.log(data);
        this.getAllNotes();
      },
      error: err => {
        console.log(err);
      }
    })
  }

  searchByLastname() {
    if (!this.searchByLastName) {
      this.errorMessageLastName = "Last Name field is empty!";
      return;
    }
    this.noteService.getNotesByLastName(this.searchByLastName).subscribe({
      next: data => {
        if (data && data.length > 0) {
          this.router.navigate(['/note-details-lastname', this.searchByLastName]).then();
        } else {
          this.errorMessageLastName = `No Note exists in the database with this last name: {${this.searchByLastName}}`;
        }

      },
      error: err => {
        console.log('Error while searching for patient by Last Name: ', err);
        this.errorMessageLastName = err.error.message;
      }
    });
  }

  searchByPatId() {
    if (!this.searchByPatientId) {
      this.errorMessagePatId = "Patient Id field is empty!";
      return;
    }
    if (!Number.isInteger(Number(this.searchByPatientId))) {
      this.errorMessagePatId = "Patient Id should be an integer!";
      return;
    }

    this.noteService.getNotesByPatId(this.searchByPatientId).subscribe({
      next: data => {
        if (data && data.length > 0) {
          this.router.navigate(['/note-details-patid', this.searchByPatientId]).then();
        } else {
          this.errorMessagePatId = `No Note exists in the database with this patient id: {${this.searchByPatientId}}`;
        }
      },
      error: err => {
        console.log('Error while searching for patient by Last Name: ', err);
        this.errorMessagePatId = err.error.message;
      }
    });
  }
}



import { NoteService } from 'src/app/services/note.service';
import { Component, OnInit } from '@angular/core';
import { Note } from 'src/app/model/note';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-note-details-lastname',
  templateUrl: './note-details-lastname.component.html',
  styleUrls: ['./note-details-lastname.component.css']
})
export class NoteDetailsLastnameComponent implements OnInit {
  notes!: Note[];
  lastName!: string;
  errorMessage!: string;

  constructor(
    private noteService: NoteService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.lastName = this.route.snapshot.params['lastname'];
    this.noteService.getNotesByLastName(this.lastName).subscribe({
      next: data => {
        console.log(data);
        this.notes = data;
      },
      error: error => {
        console.log('Error while searching for note by last name:', error);
        //this.errorMessage = `No note exists in DB with this last name :{${this.lastName}}`;
        this.errorMessage = error.error.message;
      }
    });
  }

  updateNoteById(id: string) {
    this.router.navigate(['/update-note', id]).then();
  }
  deleteNoteById(id: string) {
    this.noteService.deleteNoteById(id).subscribe({
      next: data => {
        console.log(data);
        this.router.navigate(['/note-list']).then();
      },
      error: err => {
        console.log(err);
        this.errorMessage = err.error.message;
      }
    })
  }
}

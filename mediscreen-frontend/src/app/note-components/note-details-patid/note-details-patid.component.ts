import { NoteService } from 'src/app/services/note.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Note } from 'src/app/model/note';

@Component({
  selector: 'app-note-details',
  templateUrl: './note-details-patid.component.html',
  styleUrls: ['./note-details-patid.component.css']
})
export class NoteDetailsPatidComponent implements OnInit {
  notes!: Note[];
  patId!: number;
  errorMessage!: string;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private noteService: NoteService) { }

  ngOnInit(): void {
    this.patId = this.route.snapshot.params['patid'];
    this.noteService.getNotesByPatId(this.patId).subscribe({
      next: data => {
        this.notes = data;
        console.log(data);
      },
      error: error => {
        console.log('Error while searching for note by last name:', error);
        //this.errorMessage = `No note exists in DB with this last name :{${this.patId}}`;
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

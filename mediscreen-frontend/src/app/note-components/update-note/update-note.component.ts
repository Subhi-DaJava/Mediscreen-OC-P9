import { NoteService } from 'src/app/services/note.service';
import { Note } from '../../model/note';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-note',
  templateUrl: './update-note.component.html',
  styleUrls: ['./update-note.component.css']
})
export class UpdateNoteComponent implements OnInit {
  note: Note = new Note();
  id!: string;
  errorMessage!: string;
  errorMessages!: string[];

  constructor(
    private noteService: NoteService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.getNoteById(this.id);

  }

  getNoteById(id: string) {
    this.noteService.getNoteById(id).subscribe({
      next: noteRetriedById => {
        this.note = noteRetriedById;
        console.log(noteRetriedById);
      },
      error: err => {
        console.log(err);
      }
    });
  }

  ngSubmit() {
    this.noteService.updateNoteById(this.id, this.note).subscribe({
      next: noteUpdated => {
        console.log(noteUpdated);
        this.router.navigate(['/patient-details', noteUpdated.patId]).then();
        //this.goToNoteList();
      },
      error: err => {
        console.log(err);
        this.errorMessage = err.error.message;
        this.errorMessages = err.error.errors ? err.error.errors : 'An error occurred while validating the form';
      }
    });

  }

  goToNoteList(){
    this.router.navigate(['/note-list']).then();
  }

}

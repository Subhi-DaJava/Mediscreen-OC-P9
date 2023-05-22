import { Note } from '../model/note';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class NoteService {


  private apiUrl: string= environment.NoteApiUrl;
  constructor(private http: HttpClient) { }

  getAllNotes(): Observable<Note[]> {
    return this.http.get<Note[]>(`${this.apiUrl}`);
  }

  createNewNote(note: Note): Observable<Note> {
    return this.http.post<Note>(`${this.apiUrl}/`, note);
  }

  getNoteById(id: string): Observable<Note> {
    return this.http.get<Note>(`${this.apiUrl}/${id}`);
  }

  updateNoteById(id: string, note: Note): Observable<Note> {
    return this.http.put<Note>(`${this.apiUrl}/${id}`, note);
  }

  deleteNoteById(id: string): Observable<Object> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }


  getNotesByLastName(lastName: string): Observable<Note[]> {
    return this.http.get<Note[]>(`${this.apiUrl}/by-lastName/${lastName}`);
  }
  getNotesByPatId(patId: number): Observable<Note[]> {
    return this.http.get<Note[]>(`${this.apiUrl}/by-patId/${patId}`);
  }

  addNoteToPatientByPatId(patId: number, note: string): Observable<Note> {
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' })
    };
    const body = `note=${encodeURIComponent(note)}`;
    return this.http.post<Note>(`${this.apiUrl}/${patId}`,body, httpOptions);
  }
}

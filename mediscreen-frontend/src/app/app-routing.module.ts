import { NoteListComponent } from './note-components/note-list/note-list.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CreatePatientComponent } from './patient-components/create-patient/create-patient.component';
import { PatientDetailsComponent } from './patient-components/patient-details/patient-details.component';
import { PatientListComponent } from './patient-components/patient-list/patient-list.component';
import { UpdatePatientComponent } from './patient-components/update-patient/update-patient.component';
import { UpdateNoteComponent } from './note-components/update-note/update-note.component';
import { NoteDetailsPatidComponent } from './note-components/note-details-patid/note-details-patid.component';
import { NoteDetailsLastnameComponent } from './note-components/note-details-lastname/note-details-lastname.component';
import { AddNoteToPatientComponent } from './note-components/add-note-to-patient/add-note-to-patient.component';
import {ReportComponent} from "./report-components/report/report.component";
import {CreateNoteByPatIdComponent} from "./note-components/create-note-by-pat-id/create-note-by-pat-id.component";
import {ReportByNameComponent} from "./report-components/report-by-name/report-by-name.component";

const routes: Routes = [
  { path: 'patients', component: PatientListComponent },
  { path: 'create-patient', component: CreatePatientComponent },
  { path: '', redirectTo: 'patients', pathMatch: 'full' },
  { path: 'update-patient/:id', component: UpdatePatientComponent },
  { path: 'patient-details/:id', component: PatientDetailsComponent },
  { path: 'note-list', component: NoteListComponent },
  { path: 'update-note/:id', component: UpdateNoteComponent },
  { path: 'note-details-patid/:patid', component: NoteDetailsPatidComponent },
  { path: 'note-details-lastname/:lastname', component: NoteDetailsLastnameComponent },
  { path: 'add-node-to-patient/:patid', component: AddNoteToPatientComponent },
  { path: 'report/:patId', component: ReportComponent },
  { path: 'create-note-by-pat-id/:patId', component: CreateNoteByPatIdComponent},
  { path: 'report-by-name/:patName', component: ReportByNameComponent },

];

@NgModule({
  declarations: [],
  imports: [CommonModule, RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Report} from "../model/report";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  report!: Report;


  private apiUrl: string = environment.ReportApiUrl;

  constructor(private http: HttpClient) { }

  generateReportByPatId(patId: number): Observable<Report> {
    return this.http.post<Report>(`${this.apiUrl}/id?patId=${patId}`, {});
  }

  generateReportByPatName(familyName: string): Observable<Report> {
    return this.http.post<Report>(`${this.apiUrl}/familyName?familyName=${familyName}`, {});
  }
}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Client } from './model/Client';
import { CLIENT_DATA } from './model/mock-client';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

    constructor(
        private http: HttpClient
    ) { }

    getClients(): Observable<Client[]> {
    return this.http.get<Client[]>('http://localhost:8080/client');
    }

    saveClient(client: Client): Observable<Client> {
        let url = 'http://localhost:8080/client';

        return this.http.post<Client>(url, client);
    }

    updateClient(client: Client): Observable<Client> {
        let url = 'http://localhost:8080/client' + `/${client.id}`;

        return this.http.put<Client>(url, client);
    }

    deleteClient(idClient: number): Observable<any> {
        return this.http.delete('http://localhost:8080/client/' + idClient);
    }
}

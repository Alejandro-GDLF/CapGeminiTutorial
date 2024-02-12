import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ClientService } from '../client.service';
import { Client } from '../model/Client';

@Component({
  selector: 'app-client-edit',
  templateUrl: './client-edit.component.html',
  styleUrls: ['./client-edit.component.scss']
})
export class ClientEditComponent implements OnInit {
    client: Client;

    constructor(
        public dialogRef: MatDialogRef<ClientEditComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private clientService: ClientService,
    ) { }

    ngOnInit(): void {
        this.client = this.data.client != null 
                        ? Object.assign({}, this.data.client) 
                        : new Client();
    }

    onSave() {
        this.clientService.saveClient(this.client).subscribe({
            next: data => {
                this.dialogRef.close();
            },
            error: err => {
                alert(err.error);
            }
    });
    }

    onClose() {
        this.dialogRef.close();
    }
}

import { LOCALE_ID, NgModule } from '@angular/core';
import { MAT_DATE_LOCALE } from '@angular/material/core';
import { BrowserModule } from '@angular/platform-browser';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { provideMomentDateAdapter } from '@angular/material-moment-adapter';
import es from '@angular/common/locales/es'
import { registerLocaleData } from '@angular/common';
import 'moment/locale/es';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { CategoryModule } from './category/category.module';
import { AuthorModule } from './author/author.module';
import { GameModule } from './game/game.module';
import { ClientModule } from './client/client.module';
import { GameLoanModule } from './game-loan/game-loan.module';
import moment from 'moment';

registerLocaleData(es);
moment.locale('es-ES');

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    CoreModule,
    CategoryModule,
    AuthorModule,
    GameModule,
    ClientModule,
    GameLoanModule
  ],
  providers: [
    provideAnimationsAsync(),
    {
      provide: LOCALE_ID,
      useValue: 'es-ES'
    },
    {
      provide: MAT_DATE_LOCALE,
      useValue: 'es-ES'
    },
    provideMomentDateAdapter()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

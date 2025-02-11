import { ApplicationConfig, provideZoneChangeDetection, importProvidersFrom } from '@angular/core';
import { provideRouter, Routes } from '@angular/router';

import { VERSION as MAT_VERSION, MatNativeDateModule } from '@angular/material/core';

import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';


import { HomeComponent } from "./home/home.component";
import { SchemeComponent } from "./scheme/scheme.component";
import { WorkloadComponent } from "./workload/workload.component";
import { OfficesComponent } from "./offices/offices.component";
import { RoomsComponent } from "./rooms/rooms.component";
import { GroupsComponent } from "./groups/groups.component";
import { NotFoundComponent } from "./not-found.component";

import { provideAnimations } from '@angular/platform-browser/animations';


const appRoutes: Routes = [
  { path: "", component: HomeComponent },
  { path: "offices", component: OfficesComponent },
  { path: "rooms", component: RoomsComponent },
  { path: "groups", component: GroupsComponent },
  { path: "scheme", component: SchemeComponent },
  { path: "workload", component: WorkloadComponent },
  { path: "**", component: NotFoundComponent }
];


export const appConfig: ApplicationConfig = {
  providers: [provideRouter(appRoutes), provideAnimations(), importProvidersFrom(MatNativeDateModule)]

};

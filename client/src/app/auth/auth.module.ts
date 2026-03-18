import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { LogoutComponent } from './components/logout/logout.component';

@NgModule({
  declarations: [LogoutComponent],
  imports: [CommonModule, ReactiveFormsModule, FormsModule, RouterModule],
  exports: [LogoutComponent]
})
export class AuthModule {}
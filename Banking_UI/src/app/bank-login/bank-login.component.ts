import { Component, HostListener } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-bank-login',
  templateUrl: './bank-login.component.html',
  styleUrls: ['./bank-login.component.scss']
})
export class BankLoginComponent {
  userTypeList = ['Employee', 'Admin'];
  bankForm!: FormGroup;

  constructor(private fb: FormBuilder) {
  }

  ngOnInit():void{
    this.createForm()
  }

  createForm() {
    this.bankForm = this.fb.group({
      username: this.fb.control<string>('',Validators.required),
      password: this.fb.control<string>('',Validators.required),
      usertype: this.fb.control<string>('',Validators.required),
      userType: this.fb.control<string>('')
    })
  }

  @HostListener('document:keydown.enter',['$event']) onKeydownHandler(){
    if(this.bankForm?.valid){
      this.onLogin();
    }
  }

  // get form(): {[key: string] : AbortController} {
  //   return this.userForm.controls;
  // }

  onLogin():void {

  }
}

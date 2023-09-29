import { Component, HostListener } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Signup } from '../../models/signup.model'
import { RestService } from '../rest.service';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.scss']
})
export class RegisterUserComponent {
  signUpForm!: FormGroup;
  statusForm!: FormGroup;

  customerId!: number;
  isCustomerVerified = false;

  constructor(private fb: FormBuilder, private rest:RestService) {
  }

  ngOnInit():void{
    this.createForm()
  }

  createForm() {
    this.signUpForm = this.fb.group({
      username: this.fb.control<string>('',Validators.required),
      password: this.fb.control<string>('',Validators.required),
      confirmPassword: this.fb.control<string>('',Validators.required)
    })

    this.statusForm = this.fb.group({
      accountNumber: this.fb.control<string>('',Validators.required),
      phoneNumber: this.fb.control<string>('',Validators.required)
    })
  }

  @HostListener('document:keydown.enter',['$event']) onKeydownHandler(){
    if(this.signUpForm?.valid){
      this.onSignUp(this.signUpForm.value);
    }
  }

  // get form(): {[key: string] : AbortController} {
  //   return this.userForm.controls;
  // }

  onSignUp(data:Signup):void {
    data.customerId = this.customerId;

    console.log(data)

    this.rest.registerUser(data).subscribe({
      next: (response: any) => {
        console.log(response);
      }, 
      error : (error) => {
        alert(error.error);
      }
    })
  }

  checkStatus(data: any):void {
    console.log(data);

    this.rest.checkStatus(data).subscribe({
      next: (response:any) => {
        console.log(response);
        
        this.isCustomerVerified = true;
        this.customerId = response.customerId;
        
        alert(response.message);
      }, 
      error : (error) => {
        alert(error.error);
      }
    })
  }
}

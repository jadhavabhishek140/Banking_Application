import { Component, HostListener } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestService } from '../rest.service';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.scss']
})
export class UserLoginComponent {
  userForm!: FormGroup;

  constructor(private fb: FormBuilder, private rest: RestService) {
  }

  ngOnInit():void{
    this.createForm()
  }

  createForm() {
    this.userForm = this.fb.group({
      username: this.fb.control<string>('',Validators.required),
      password: this.fb.control<string>('',Validators.required)
    })
  }

  @HostListener('document:keydown.enter',['$event']) onKeydownHandler(){
    if(this.userForm?.valid){
      this.onLogin(this.userForm.value);
    }
  }

  // get form(): {[key: string] : AbortController} {
  //   return this.userForm.controls;
  // }


  onLogin(data: any):void {
    console.log(data);

    this.rest.loginUser(data).subscribe({
      next: (response) => {
        console.log(response);
        alert("User LoggedIn Successfully.");
      }, 
      error : (error) => {
        alert(error.error);
      }
    })
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionapiService } from 'src/app/services/sessionapi.service';
import { UserapiService } from 'src/app/services/userapi.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  
  feedbackIsVisible : boolean = false;

  username: string = "";
  password: string = "";
  firstname: string = "";
  lastname: string = "";
  email: string = "";

  User : any = {
    username : this.username,
    password : this.password,
    firstName : this.firstname,
    lastName : this.lastname,
    email : this.email,
    role: {roleId : 2}
  }

  constructor(private router : Router, private route : ActivatedRoute, private userApi : UserapiService, private sessionApi : SessionapiService) { }

  ngOnInit(): void {
    this.sessionApi.checkSession().subscribe(response => {
      if(response.success == true){
        this.router.navigate(['/home'], { queryParams: {id: response.data.userId}});
      }
    });
  }

  register(){
    let User : any = {
      username : this.username,
      password : this.password,
      firstName : this.firstname,
      lastName : this.lastname,
      email : this.email,
      role: {roleId : 2}
    }
    
    const userIsNotEmpty : boolean = Object.values(User).every(x => x != "");

    if (userIsNotEmpty){
      this.userApi.create(User).subscribe(response => {
        if (response.sucess == true) {
          this.router.navigate([""]);
        } else {
          this.feedbackIsVisible = true;
        }
      })
    }
  }

}

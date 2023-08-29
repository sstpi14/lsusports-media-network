import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router'
import { UserapiService } from 'src/app/services/userapi.service';
import { SessionapiService } from 'src/app/services/sessionapi.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string = "";
  password: string = "";
  user:any = {};
  id:number=0;

  feedbackIsVisible : boolean = false;

  constructor(private userApi : UserapiService, private sessionApi : SessionapiService, private router : Router, private route : ActivatedRoute) { }

  ngOnInit(): void {
    this.sessionApi.checkSession().subscribe(response => {
      if(response.success == true){
        this.router.navigate(['/home'], { queryParams: {id: response.data.userId}});
      }
    });
  }

  login(){
    let user:any = {
      username: this.username,
      password: this.password
    }

    if (user.username != "" && user.password !=""){
      this.sessionApi.login(user).subscribe(response => {
        if (response.success == false){
          this.feedbackIsVisible = true;
        } else {
          this.router.navigate(['/home'], { queryParams: {id: response.data.userId}});
        }
      }) 
    }
  }

  
}

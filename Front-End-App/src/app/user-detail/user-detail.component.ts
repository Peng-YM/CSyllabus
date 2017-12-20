import { Component, OnInit } from '@angular/core';
import {User} from "../Models/user";
import {UserService} from "../user.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {
  user: User;
  constructor(
    private userService: UserService,
    private route: ActivatedRoute
  ) { }
  ngOnInit() {
  }

}

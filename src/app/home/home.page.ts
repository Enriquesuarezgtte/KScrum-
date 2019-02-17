import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  slides = [
    {
      title: "Welcome to KScrum!",
      description: "This app will be useful to manage your project. Here there are some tools:",
      image: "assets/img/ica-slidebox-img-1.png",
    },
    {
      title: "What can you do?",
      description: "<b>Dailys</b> Create and edit dailys in your Scrum project.",
      image: "assets/img/ica-slidebox-img-2.png",
    },
    {
      title: "Problem with tasks?",
      description: "Add and asign task to developers.",
      image: "assets/img/ica-slidebox-img-3.png",
    }
];
}

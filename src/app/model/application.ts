import {User} from "./user";
import {Job} from "./job";
import {notification} from "./notification";

export class Application implements notification{
  id: number;
  user: User;
  job: Job;
  createdDate: Date;
  text: string;
}

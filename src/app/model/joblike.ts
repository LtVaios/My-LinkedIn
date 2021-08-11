import {User} from "./user";
import {Job} from "./job";

export class JobLike implements notification{
  id: number;
  user: User;
  job: Job;
  createdDate: Date;
}

export interface notification {
  user:User;
  createdDate: Date;
}

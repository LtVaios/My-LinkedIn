import {User} from "./user";
import {Job} from "./job";

export class JobLike {
  id: number;
  user: User;
  job: Job;
  createdDate: Date;
}

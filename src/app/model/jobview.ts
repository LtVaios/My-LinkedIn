import {User} from "./user";
import {Job} from "./job";

export class JobView {
  id: number;
  user: User;
  job: Job;
  createdDate: Date;
}

import {User} from "./user";

export class Job {
  id: number;
  user: User;
  body: string;
  createdDate: Date;
}

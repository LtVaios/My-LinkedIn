import {User} from "./user";

export class Job {
  id: number;
  user: User;
  likes: Set<User>;
  body: string;
  createdDate: Date;
}

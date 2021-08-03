import {User} from "./user";


export class Post {
  id:number;
  post_body: string;
  user_id: number;
  user: User;
  date_time: string;
}

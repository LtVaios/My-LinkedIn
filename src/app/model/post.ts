import {User} from "./user";


export class Post {
  id:number;
  post_body: string;
  user: User;
  date_time: string;
  images: any[];
}

import {User} from "./user";
import {Image} from "./image";

export class Post {
  id:number;
  post_body: string;
  user: User;
  date_time: string;
  images: Image[];
}

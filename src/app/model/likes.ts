import {User} from "./user";
import {Post} from "./post";
import {notification} from "./joblike";

export class Likes implements notification{
  id:number;
  post: Post;
  user: User;
  createdDate: Date;
}

import {User} from "./user";
import {Post} from "./post";
import {notification} from "./notification"

export class Comment implements notification{
  id:number;
  comment_text: string;
  user: User;
  post: Post;
  createdDate: Date;
}

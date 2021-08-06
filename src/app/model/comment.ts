import {User} from "./user";
import {Post} from "./post";

export class Comment {
  id:number;
  comment_text: string;
  user: User;
  post: Post;
}

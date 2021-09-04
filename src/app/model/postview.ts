import {User} from "./user";
import {Post} from "./post";

export class PostView {
  id: number;
  user: User;
  post: Post;
  createdDate: Date;
}

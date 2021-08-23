import {User} from "./user";
import {Image} from "./image";
import {Video} from "./video"

export class Post {
  id:number;
  post_body: string;
  user: User;
  date_time: string;
  images: Image[];
  videos: Video[];
}

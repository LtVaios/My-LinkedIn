import {User} from "./user";
import {Image} from "./image";
import {Video} from "./video"
import {Audio_} from "./audio";

export class Post {
  id:number;
  post_body: string;
  user: User;
  date_time: string;
  images: Image[];
  videos: Video[];
  audios: Audio_[];
}

import {User} from "./user";
import {Image} from "./image";
import {Audio_} from "./audio";

export class Post {
  id:number;
  post_body: string;
  user: User;
  date_time: string;
  images: Image[];
  audios: Audio_[];
}

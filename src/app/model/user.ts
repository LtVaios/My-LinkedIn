import {Image} from "./image";

export class User {
  id: number;
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  phone: string;
  admin: boolean;
  work_experience: string;
  work_experience_public: boolean;
  education: string;
  education_public: boolean;
  skills: string;
  skills_public: boolean;
  img: Image;
}

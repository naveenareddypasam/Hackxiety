/**
 * REFACTORING AND STRICT TYPE CHECK REQUIRED
 */
import {User as UserDetails} from "../types/user.type";

import {NextFunction, Request, Response} from "express";
import {db} from "../server";
import {ObjectId, WithId} from "mongodb";
import HTTP from "../utils/statusCodeConfig";
export default class User {
  constructor() {}

  async getUsers(req: Request, res: Response, next: NextFunction) {
    const users = <WithId<UserDetails>[]>(
      await db.collection("Users").find().toArray()
    );
    res.status(HTTP.OK).json(users);
  }

  async getProfile(req: Request, res: Response, next: NextFunction) {
    const user = <WithId<UserDetails>>await db.collection("Users").findOne(
      {_id: new ObjectId((req as any).user._id)},
      {
        projection: {
          username: 1,
          phone_number: 1,
          email: 1,
          emergency_contact: 1,
        },
      }
    );
    res.status(HTTP.OK).json({
      ...user,
      image: process.env.MINIO_IMAGE_URL!.replace("%USERNAME%", user.username),
    });
  }

  async uploadImage(req: Request, res: Response, next: NextFunction) {
    res.status(HTTP.OK).json({
      message: "Image uploaded",
    });
  }
}

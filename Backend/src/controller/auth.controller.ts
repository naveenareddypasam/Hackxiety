import {NextFunction, Request, Response} from "express";
import {db} from "../server";
import Bcrypt from "../utils/bcrypt";
import {Document, ObjectId, WithId} from "mongodb";
import jwt from "jsonwebtoken";
import {User as UserDetails} from "../types/user.type";
import {assert} from "console";
import CustomError from "../utils/CustomError";
import HTTP from "../utils/statusCodeConfig";
export default class Auth {
  private bcrypt: Bcrypt = new Bcrypt();
  constructor() {}

  async signup(req: Request, res: Response, next: NextFunction) {
    const {
      username,
      email,
      password,
      confirm_password,
      phone_number,
      emergency_contact,
    }: {
      username: string;
      email: string;
      password: string;
      confirm_password: string;
      phone_number: string;
      emergency_contact: string;
    } = {...req.body};

    if (
      !username ||
      !email ||
      !password ||
      !confirm_password ||
      !phone_number ||
      !emergency_contact
    ) {
      const error = new CustomError(
        "Users must provide 'username', 'email', 'password', 'confirm_password', 'phone_number', 'emergency_contact' field.",
        HTTP.BAD_REQUEST
      );
      return next(error);
    }

    if (phone_number.length != 10) {
      const error = new CustomError("Invalid phone number", HTTP.BAD_REQUEST);

      return next(error);
    }

    if (password !== confirm_password) {
      const error = new CustomError(
        "Password fields does not match",
        HTTP.BAD_REQUEST
      );
      return next(error);
    }

    const user = <WithId<UserDetails>[]>await db
      .collection("Users")
      .find({$or: [{username}, {email}, {phone_number}]})
      .toArray();

    if (user.length > 0) {
      const error = new CustomError(
        "Username or email or phone number alreaddy taken.",
        HTTP.BAD_REQUEST
      );

      return next(error);
    }
    const hashedPassword = await this.bcrypt.hashPassword(password);

    await this.insertUser(
      username,
      hashedPassword,
      email,
      phone_number,
      emergency_contact
    );

    res.status(HTTP.OK).json({
      message: "You're signed up. Please login.",
    });
  }

  async login(req: Request, res: Response, next: NextFunction) {
    const {username, password}: {username: string; password: string} = req.body;

    if (!username || !password) {
      const error = new CustomError(
        "Username or Password not provided.",
        HTTP.UNAUTHORIZED
      );
      return next(error);
    }

    const user = <WithId<UserDetails>>(
      await db.collection("Users").findOne({username})
    );

    if (!user || !(await this.bcrypt.checkPassword(user.password, password))) {
      const error = new CustomError(
        "Username or Password does not match",
        HTTP.UNAUTHORIZED
      );

      return next(error);
    }

    const token = jwt.sign({userId: user._id}, process.env.JWT_SECRET!, {
      expiresIn: "7d",
    });

    res.status(HTTP.OK).json({
      messsage: "Logged in.",
      token,
    });
  }

  async isAuthenticated(req: Request, res: Response, next: NextFunction) {
    try {
      if (
        !req.headers.authorization ||
        (req.headers.authorization &&
          req.headers.authorization.split(" ")[0] !== "Bearer")
      ) {
        const error = new CustomError(
          "You are not authenticated.",
          HTTP.UNAUTHORIZED
        );
        return next(error);
      }

      const token = req.headers.authorization.split(" ")[1];

      const decodedData = new Promise((resolve, reject) => {
        jwt.verify(token, process.env.JWT_SECRET!, (err, payload) => {
          if (!err) {
            return resolve(payload);
          }

          const error = new CustomError("falied", HTTP.UNAUTHORIZED);
          return next(error);
        });
      });

      (req as any).user = <WithId<UserDetails>>(
        await db
          .collection("Users")
          .findOne({_id: new ObjectId(((await decodedData) as any).userId)})
      );

      next();
    } catch (err) {
      res.status(HTTP.UNAUTHORIZED).json({message: "Ivalid JWT"});
    }
  }

  async isAuthorised(req: Request, res: Response, next: NextFunction) {
    const user = <WithId<UserDetails>>(
      await db
        .collection("Users")
        .findOne({_id: new ObjectId((req as any).user._id)})
    );

    if (user.role !== "admin") {
      const error = new CustomError(
        "You are not authorised to access this feature.",
        HTTP.FORIBIDDEN
      );
      return next(error);
    }

    next();
  }

  private async insertUser(
    username: string,
    hashedPassword: string,
    email: string,
    phone_number: string,
    emergency_contact: string
  ) {
    await db.collection("Users").insertOne({
      username,
      password: hashedPassword,
      role: "user",
      email,
      phone_number,
      emergency_contact,
      created_at: new Date(),
      updated_at: new Date(),
    });
  }
}

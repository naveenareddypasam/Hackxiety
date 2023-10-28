import {Request, Response, NextFunction} from "express";
import CustomError from "./CustomError";
import HTTP from "./statusCodeConfig";

const asyncWrapper = (
  fn: (req: Request, res: Response, next: NextFunction) => Promise<any>
) => {
  return (req: Request, res: Response, next: NextFunction) => {
    fn(req, res, next).catch(() =>
      next(new CustomError("Something went wrong.", HTTP.INTERNAL_SERVER_ERROR))
    );
  };
};

export default asyncWrapper;

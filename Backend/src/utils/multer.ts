import multer from "multer";

import {NextFunction, Request, Response} from "express";
export const upload = multer({
  storage: multer.memoryStorage(),
  fileFilter: function (
    req: Request,
    file: Express.Multer.File,
    cb: multer.FileFilterCallback
  ) {
    if (file.mimetype.startsWith("image")) {
      cb(null, true);
    } else {
      cb(new Error("You can only upload Image here"));
    }
  },
});

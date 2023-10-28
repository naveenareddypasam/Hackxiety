import express, {Router} from "express";
import User from "../controller/user.controller";
import Auth from "../controller/auth.controller";
import {upload} from "../utils/multer";
import {resizeImgage} from "../utils/sharp";
import asyncWrapper from "../utils/asyncWrapper";

const user = new User();
const auth = new Auth();
const router: Router = express.Router();

router.get(
  "/",
  auth.isAuthenticated,
  auth.isAuthorised,
  asyncWrapper(user.getUsers)
);
router.post("/signup", asyncWrapper(auth.signup.bind(auth)));
router.post("/login", asyncWrapper(auth.login.bind(auth)));
router.get("/profile", auth.isAuthenticated, asyncWrapper(user.getProfile));
router.patch(
  "/profile-image",
  auth.isAuthenticated,
  upload.single("image"),
  resizeImgage,
  asyncWrapper(user.uploadImage)
);

export default router;

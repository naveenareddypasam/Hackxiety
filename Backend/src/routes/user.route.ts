import express, {Router} from "express";
import User from "../controller/user.controller";
import Auth from "../controller/auth.controller";
import {upload} from "../utils/multer";
import {resizeImgage} from "../utils/sharp";

const user = new User();
const auth = new Auth();
const router: Router = express.Router();

router.get("/", auth.isAuthenticated, auth.isAuthorised, user.getUsers);
router.post("/signup", auth.signup);
router.post("/login", auth.login);
router.get("/profile", auth.isAuthenticated, user.getProfile);
router.patch(
  "/profile-image",
  auth.isAuthenticated,
  upload.single("image"),
  resizeImgage,
  user.uploadImage
);

export default router;

import express, {Router} from "express";
import Auth from "../controller/auth.controller";
import Journal from "../controller/journal.controller";
import asyncWrapper from "../utils/asyncWrapper";

const auth = new Auth();
const journal = new Journal();
const router: Router = express.Router();

router.get("/", auth.isAuthenticated, asyncWrapper(journal.getJournals));
router.post("/", auth.isAuthenticated, asyncWrapper(journal.creatJournal));

export default router;

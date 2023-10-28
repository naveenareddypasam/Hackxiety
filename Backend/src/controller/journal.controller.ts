import {NextFunction, Request, Response} from "express";
import {db} from "../server";
import HTTP from "../utils/statusCodeConfig";

export default class Journal {
  constructor() {}

  async creatJournal(req: Request, res: Response, next: NextFunction) {
    const {body} = req;

    await db
      .collection("Journals")
      .insertOne({user_id: (req as any).user._id, journal: body.journal});
    res.status(HTTP.CREATED).json({
      message: "Journal created",
    });
  }

  async getJournals(req: Request, res: Response, next: NextFunction) {
    const journals = await db
      .collection("Users")
      .aggregate([
        {$match: {username: (req as any).user.username}},
        {
          $lookup: {
            from: "Journals",
            localField: "_id",
            foreignField: "user_id",
            as: "journals",
          },
        },
        {
          $project: {journals: 1, _id: 0},
        },
      ])
      .toArray();

    res.status(HTTP.OK).json({journals: journals[0].journals});
  }
}

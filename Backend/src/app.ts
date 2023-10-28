import express, {Express, NextFunction, Request, Response} from "express";
import userRoute from "./routes/user.route";
import journalRoute from "./routes/journal.route";
import mongoSanitize from "express-mongo-sanitize";
import CustomError from "./utils/CustomError";
import {rateLimit} from "express-rate-limit";


const app: Express = express();

app.use(express.json());

app.use(mongoSanitize());

const limiter = rateLimit({
  max: 100,
  windowMs: 15 * 60 * 1000,
  message: JSON.stringify({
    message: "Too many requests sent from your IP. Please try after an hour.",
  }),
});
app.use("/v1",limiter)
app.use("/v1/users", userRoute);
app.use("/v1/journals", journalRoute);

app.use("*", (req: Request, res: Response, next: NextFunction) => {
  return next(
    new CustomError(`Url '${req.baseUrl}' does not exists in this server.`, 404)
  );
});

app.use((err: CustomError, req: Request, res: Response, next: NextFunction) => {
  res.status(err.statusCode).json({
    ...err,
    message: err.message,
  });
});

export default app;

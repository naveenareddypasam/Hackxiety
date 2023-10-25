import Sharp from "sharp";
import {NextFunction, Request, Response} from "express";
import {minioClient} from "../server";

export const resizeImgage = async (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  const extension = req.file?.mimetype.split("/")[1];

  let metaData = {
    "Content-Type": `image/${extension}`,
  };
  const isBucketExists = await minioClient.bucketExists(
    process.env.MINIO_BUCKET_NAME!
  );

  if (!isBucketExists) {
    console.log(new Date(), ": Bucket does not exist.");
    console.log(new Date(), ": Creating bucket.");
    await minioClient.makeBucket(process.env.MINIO_BUCKET_NAME!, "us-east-1");
    console.log(new Date(), ": Bucket created.");
  }
  Sharp(req.file?.buffer)
    .resize(80, 80, {
      fit: "contain",
    })
    .toBuffer()
    .then((res: any) => {
      return minioClient.putObject(
        process.env.MINIO_BUCKET_NAME!,
        (req as any).user.username,
        res,
        undefined,
        metaData
      );
    })
    .then(() => {
      console.log("File wirtten to minio");
    })
    .catch((err: Error) => {
      console.log("Something went wrong while writing to minio", err);
    });

  next();
};

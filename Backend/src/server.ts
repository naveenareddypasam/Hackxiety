import app from "./app";
import {Db, MongoClient} from "mongodb";
import * as MinioServer from "minio";

const port: number = Number(process.env.PORT) ?? 3000;

const dbName: string = process.env.DB_NAME!;
const dbUrl: string = process.env.DB_URL!;
const client: MongoClient = new MongoClient(dbUrl);

client.connect().then((res) => {
  console.log("DB CONNECTED SUCCESSFULLY");
});

const server = app.listen(port, () => {
  console.log(`Server is up at http://localhost:${port}`);
});

export const minioClient = new MinioServer.Client({
  endPoint: process.env.MINIO_HOST ?? "localhost",
  accessKey: process.env.MINIO_ACCESS_KEY ?? "ABCD",
  port: Number(process.env.MINIO_PORT) ?? 9000,
  secretKey: process.env.MINIO_SECRET_KEY ?? "TESTSERVER",
  useSSL: false,
});

export const db: Db = client.db(dbName);

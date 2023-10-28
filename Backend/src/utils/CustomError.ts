export default class CustomError extends Error {
  private status: string;
  private isOperationalError: boolean;

  constructor(public message: string, public statusCode: number) {
    super(message);

    this.status = `${this.statusCode}`.startsWith("5") ? "error" : "fail";
    this.isOperationalError = true;
  }
}

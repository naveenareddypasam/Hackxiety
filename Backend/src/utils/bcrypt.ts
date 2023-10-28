import bcrypt from "bcryptjs";

export default class Bcrypt {
  constructor() {}

  async hashPassword(password: string) {
    return await bcrypt.hash(password, 10);
  }

  async checkPassword(hashedPassword: string, plainPassword: string) {
    return await bcrypt.compare(plainPassword, hashedPassword);
  }
}

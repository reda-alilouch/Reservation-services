export type UserRole = "ADMIN" | "CLIENT" | "PRESTATAIRE";

export interface User {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  role: UserRole;
  isActive: boolean;
  createdAt: string;
}

export interface LoginInput {
  email: string;
  password: string;
}

export interface RegisterInput extends LoginInput {
  firstName: string;
  lastName: string;
  role?: UserRole;
}

export interface AuthResponse {
  tokenType: string;
  userId: string;
  email: string;
  role: UserRole;
}

import { api } from "./api";
import type { AuthResponse, LoginInput, RegisterInput, User } from "@/types/user";

export const authApi = {
  
  login: (input: LoginInput) =>
    api<AuthResponse>("/auth/login", { method: "POST", body: input }),

  register: (input: RegisterInput) =>
    api<AuthResponse>("/auth/register", { method: "POST", body: input }),

  logout: () => api<void>("/auth/logout", { method: "POST" }),

  refresh: () => api<AuthResponse>("/auth/refresh", { method: "POST" }),
};

export const userApi = {

  getMe: () => api<User>("/users/me"),

  getAll: () => api<User[]>("/users"),

  updateMe: (input: Pick<User, "firstName" | "lastName">) =>
    api<User>("/users/me", { method: "PUT", body: input }),

  updateStatus: (id: string, active: boolean) =>
    api<void>(`/users/${id}/status?active=${active}`, { method: "PUT" }),
};

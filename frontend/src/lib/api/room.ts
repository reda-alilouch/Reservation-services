import { api } from "./api";
import type { CreateRoomInput, Room, UpdateRoomInput } from "@/types/room";

const ROOMS_PATH = "/api/hotels/room";

export const roomApi = {
  getAll: () => api<Room[]>(ROOMS_PATH),
  getById: (id: string) => api<Room>(`${ROOMS_PATH}/${id}`),
  create: (input: CreateRoomInput) => api<Room>(ROOMS_PATH, { method: "POST", body: input }),
  update: (id: string, input: UpdateRoomInput) =>
    api<Room>(`${ROOMS_PATH}/${id}`, { method: "PUT", body: input }),
  delete: (id: string) => api<void>(`${ROOMS_PATH}/${id}`, { method: "DELETE" }),
};

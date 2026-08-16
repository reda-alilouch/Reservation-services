import { api } from "./api";
import type { CreateHotelInput, Hotel, UpdateHotelInput } from "@/types/hotel";

const HOTELS_PATH = "/api/hotel";

export const hotelApi = {
  getAll: () => api<Hotel[]>(HOTELS_PATH),

  getById: (id: string) => api<Hotel>(`${HOTELS_PATH}/${id}`),

  create: (input: CreateHotelInput) =>
    api<Hotel>(HOTELS_PATH, { method: "POST", body: input }),

  update: (id: string, input: UpdateHotelInput) =>
    api<Hotel>(`${HOTELS_PATH}/${id}`, { method: "PUT", body: input }),

  archive: (id: string) =>
    api<void>(`${HOTELS_PATH}/${id}`, { method: "PUT" }),
};

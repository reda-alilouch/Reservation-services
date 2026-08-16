import { z } from "zod";
import { ROOM_TYPES } from "@/types/room";

const roomFields = {
  roomNumber: z.string().trim().min(1, "Le numéro est obligatoire").max(50),
  name: z.string().trim().min(1, "Le nom est obligatoire").max(255),
  description: z.string().trim().max(2_000).optional(),
  roomType: z.enum(ROOM_TYPES),
  capacity: z.coerce.number().int().positive("La capacité doit être supérieure à zéro"),
  bedCount: z.coerce.number().int().positive("Le nombre de lits doit être supérieur à zéro"),
  bedType: z.string().trim().max(100).optional(),
  pricePerNight: z.coerce.number().positive("Le prix doit être supérieur à zéro"),
  totalRooms: z.coerce.number().int().positive("Le total doit être supérieur à zéro"),
  availableRooms: z.coerce.number().int().min(0, "Le nombre disponible ne peut pas être négatif"),
};

export const createRoomSchema = z
  .object({ hotelId: z.string().uuid("Hôtel invalide"), ...roomFields })
  .refine(({ availableRooms, totalRooms }) => availableRooms <= totalRooms, {
    message: "Le nombre disponible ne peut pas dépasser le total",
    path: ["availableRooms"],
  });

export const updateRoomSchema = z
  .object(roomFields)
  .refine(({ availableRooms, totalRooms }) => availableRooms <= totalRooms, {
    message: "Le nombre disponible ne peut pas dépasser le total",
    path: ["availableRooms"],
  });

export type CreateRoomFormValues = z.output<typeof createRoomSchema>;
export type UpdateRoomFormValues = z.output<typeof updateRoomSchema>;

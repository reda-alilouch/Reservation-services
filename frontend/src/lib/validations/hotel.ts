import { z } from "zod";

const hotelFields = {
  name: z.string().trim().min(1, "Le nom est obligatoire").max(255),
  description: z.string().trim().max(2_000).optional(),
  address: z.string().trim().min(1, "L'adresse est obligatoire").max(255),
  city: z.string().trim().min(1, "La ville est obligatoire").max(100),
  country: z.string().trim().min(1, "Le pays est obligatoire").max(100),
};

export const createHotelSchema = z.object({
  providerId: z.string().uuid("Prestataire invalide"),
  ...hotelFields,
});

export const updateHotelSchema = z.object(hotelFields);

export type CreateHotelFormValues = z.infer<typeof createHotelSchema>;
export type UpdateHotelFormValues = z.infer<typeof updateHotelSchema>;

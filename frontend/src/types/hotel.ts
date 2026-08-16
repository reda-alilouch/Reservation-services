export type HotelStatus = "ACTIVE" | "INACTIVE" | "ARCHIVED";

export interface Hotel {
  id: string;
  providerId: string;
  name: string;
  description: string | null;
  address: string;
  city: string;
  country: string;
  status: HotelStatus;
}

export interface CreateHotelInput {
  providerId: string;
  name: string;
  description?: string;
  address: string;
  city: string;
  country: string;
}

export type UpdateHotelInput = Omit<CreateHotelInput, "providerId">;

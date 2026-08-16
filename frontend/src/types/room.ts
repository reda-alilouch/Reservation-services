export const ROOM_TYPES = ["SINGLE", "DOUBLE", "TWIN", "TRIPLE", "SUITE", "FAMILY"] as const;

export type RoomType = (typeof ROOM_TYPES)[number];

export interface Room {
  id: string;
  hotelId: string;
  roomNumber: string;
  name: string;
  description: string | null;
  roomType: RoomType;
  capacity: number;
  bedCount: number;
  bedType: string | null;
  pricePerNight: number;
  totalRooms: number;
  availableRooms: number;
}

export interface CreateRoomInput {
  hotelId: string;
  roomNumber: string;
  name: string;
  description?: string;
  roomType: RoomType;
  capacity: number;
  bedCount: number;
  bedType?: string;
  pricePerNight: number;
  totalRooms: number;
  availableRooms: number;
}

export type UpdateRoomInput = Omit<CreateRoomInput, "hotelId">;

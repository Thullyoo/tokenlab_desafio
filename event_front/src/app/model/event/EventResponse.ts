import type { UserUpdateRequest } from "./UserEventResponse";

export interface EventResponse{
  id: number;
  description: string;
  startTime: string; 
  endTime: string;  
  userCreator: string;
  members: UserUpdateRequest[]
}
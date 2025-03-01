import type { UserUpdateRequest } from "./UserEventResponse";

export interface EventResponse{
  description: string;
  startTime: string; 
  endTime: string;  
  userCreator: string;
  members: UserUpdateRequest[]
}
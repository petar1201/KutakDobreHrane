import { ReservationDto } from "./reservationDto";

export class ReservationArchive{
    active: ReservationDto[]
    expired: ReservationDto[]
    refused: ReservationDto[]
}
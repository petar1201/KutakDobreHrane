import UserStatus from "./userStatus";
import UserType from "./userType";

export class User {
    id: Number;
    username: String;
    password: String;
    type: UserType;
    status: UserStatus;
    securityQuestion: String;
    securityAnswer: String;
    name: String;
    lastName: String;
    gender: String;
    address: String;
    phoneNumber: String;
    email: String;
    profilePhoto: String;
    cardNumber: String;
    idRes:Number = null;
    
}

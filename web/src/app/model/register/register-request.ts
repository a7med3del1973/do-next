export class RegisterRequest {
    constructor(
        private firstName: string,
        private lastName: string,
        private email: string,
        private password: string
    ) {}
}

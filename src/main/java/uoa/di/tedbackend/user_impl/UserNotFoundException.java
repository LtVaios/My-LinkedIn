package uoa.di.tedbackend.user_impl;

class UserNotFoundException extends RuntimeException {

    UserNotFoundException() {
        super("Something went wrong while trying to fetch user.");
    }
}

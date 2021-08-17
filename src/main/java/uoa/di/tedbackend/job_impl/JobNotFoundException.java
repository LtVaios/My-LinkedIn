package uoa.di.tedbackend.job_impl;

class JobNotFoundException extends RuntimeException {

    JobNotFoundException() {
        super("Something went wrong while trying to fetch jpb.");
    }
}

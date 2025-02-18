# Base Spring Boot Project

This is start-kit project for Spring Boot applications. It includes the following features:
- JWT authentication
- Google OAuth2 authentication
- PostgreSQL database configuration
- Gradle build system

## Requirements

- Java 17\+
- Gradle
- A modern IDE like IntelliJ IDEA 2024.1.7

## Setup

1. Clone this repository and open it in `IntelliJ IDEA 2024.1.7`.
2. Ensure Gradle is configured in your IDE.
3. Set up environment variables for:

   | Variable        | Description                                      |
     |-----------------|--------------------------------------------------|
   | `JWT_SECRET`    | The secret key used for signing JWT tokens.      |
   | `JWT_EXPIRATION`| The expiration time for the JWT tokens in milliseconds. |
   | `DB_URL`        | The URL of the database.                        |
   | `DB_USERNAME`   | The username for the database.                   |
   | `DB_PASSWORD`   | The password for the database.                   |
   | `GOOGLE_CLIENT_ID` | The client ID for Google OAuth2 authentication. |
   | `GOOGLE_CLIENT_SECRET` | The client secret for Google OAuth2 authentication. |
   | `GOOGLE_REDIRECT_URI` | The redirect URI for Google OAuth2 authentication. |

4. Run the application using Gradle tasks or directly from your IDE.

## Usage

- The application starts on the configured port.
- OAuth2 flow can be tested using Google authentication.
- The JWT is generated and appended to the redirect URL upon successful sign-in.